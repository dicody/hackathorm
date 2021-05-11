/*
Copyright 2021.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package participant

import (
	"context"
	"github.com/go-logr/logr"
	appsv1 "k8s.io/api/apps/v1"
	corev1 "k8s.io/api/core/v1"
	metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
	"k8s.io/apimachinery/pkg/runtime"
	"k8s.io/apimachinery/pkg/types"
	ctrl "sigs.k8s.io/controller-runtime"
	"sigs.k8s.io/controller-runtime/pkg/client"

	participantv1 "github.com/dicody/hackathorm/apis/participant/v1"
)

// HackathormParticipantReconciler reconciles a HackathormParticipant object
type HackathormParticipantReconciler struct {
	client.Client
	Log    logr.Logger
	Scheme *runtime.Scheme
}

//+kubebuilder:rbac:groups=participant.hackathorm.org,resources=hackathormparticipants,verbs=get;list;watch;create;update;patch;delete
//+kubebuilder:rbac:groups=participant.hackathorm.org,resources=hackathormparticipants/status,verbs=get;update;patch
//+kubebuilder:rbac:groups=participant.hackathorm.org,resources=hackathormparticipants/finalizers,verbs=update

// Reconcile is part of the main kubernetes reconciliation loop which aims to
// move the current state of the cluster closer to the desired state.
// TODO(user): Modify the Reconcile function to compare the state specified by
// the HackathormParticipant object against the actual cluster state, and then
// perform operations to make the cluster state reflect the state specified by
// the user.
//
// For more details, check Reconcile and its Result here:
// - https://pkg.go.dev/sigs.k8s.io/controller-runtime@v0.7.2/pkg/reconcile
func (r *HackathormParticipantReconciler) Reconcile(ctx context.Context, req ctrl.Request) (ctrl.Result, error) {
	log := r.Log.WithValues("hackathormparticipant", req.NamespacedName)
	log.Info("reconcile execution started")

	// player's meta
	labels := map[string]string{
		// todo read app name from spec
		"app": "nginx",
	}
	// todo read replicas from spec
	replicas := int32(0)
	objectMeta := metav1.ObjectMeta{
		Name:      labels["app"],
		Namespace: req.Namespace,
		Labels:    labels,
	}

	// validate participant state
	participant := &participantv1.HackathormParticipant{}
	err := r.Client.Get(ctx, req.NamespacedName, participant)
	if err != nil {
		log.Info("Participant deleted, cleaning previously created resources..")
		err = r.Client.Delete(ctx, &appsv1.Deployment{ObjectMeta: objectMeta})
		err = r.Client.Delete(ctx, &corev1.Service{ObjectMeta: objectMeta})
		if err != nil {
			log.Error(err, "error on resources clean up!")
		}
		log.Info("resources clean up finished")
		return ctrl.Result{}, client.IgnoreNotFound(err)
	}
	log.Info("got participant", "spec", participant.Spec)

	// create deployment and service
	objectKey := types.NamespacedName{
		Namespace: req.Namespace,
		Name:      labels["app"],
	}

	// deployment
	deployment := &appsv1.Deployment{}
	err = r.Client.Get(ctx, objectKey, deployment)
	if err == nil {
		log.Info("deployment already exists")
	} else {
		err = r.Client.Create(ctx, &appsv1.Deployment{
			ObjectMeta: objectMeta,
			Spec: appsv1.DeploymentSpec{
				Replicas: &replicas,
				Selector: &metav1.LabelSelector{MatchLabels: labels},
				Template: corev1.PodTemplateSpec{
					ObjectMeta: metav1.ObjectMeta{Labels: labels},
					Spec: corev1.PodSpec{
						Containers: []corev1.Container{{
							Name:  "nginx",
							Image: "nginx:latest",
							Ports: []corev1.ContainerPort{{
								ContainerPort: 80,
							}},
						}},
					},
				},
			},
		})

		if err != nil {
			log.Error(err, "failed to create deployment!")
			return ctrl.Result{}, err
		}
	}

	// service
	service := &corev1.Service{}
	err = r.Client.Get(ctx, objectKey, service)
	if err == nil {
		log.Info("service already exists")
	} else {
		err = r.Client.Create(ctx, &corev1.Service{
			ObjectMeta: objectMeta,
			Spec: corev1.ServiceSpec{
				Ports:    []corev1.ServicePort{{Port: 80, Protocol: corev1.ProtocolTCP}},
				Selector: labels,
			},
		})
		if err != nil {
			log.Error(err, "failed to create service!")
			return ctrl.Result{}, err
		}
	}

	log.Info("reconcile execution finished")
	return ctrl.Result{}, nil
}

// SetupWithManager sets up the controller with the Manager.
func (r *HackathormParticipantReconciler) SetupWithManager(mgr ctrl.Manager) error {
	return ctrl.NewControllerManagedBy(mgr).
		For(&participantv1.HackathormParticipant{}).
		Owns(&appsv1.Deployment{}).
		Owns(&corev1.Service{}).
		Complete(r)
}
