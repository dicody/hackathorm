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

package event

import (
	"context"
	eventv1 "github.com/dicody/hackathorm/apis/event/v1"
	participantv1 "github.com/dicody/hackathorm/apis/participant/v1"
	"github.com/go-logr/logr"
	"k8s.io/apimachinery/pkg/runtime"
	"k8s.io/client-go/tools/reference"
	ctrl "sigs.k8s.io/controller-runtime"
	"sigs.k8s.io/controller-runtime/pkg/client"
	"sigs.k8s.io/controller-runtime/pkg/handler"
	"sigs.k8s.io/controller-runtime/pkg/source"
	"time"
)

// HackathormEventReconciler reconciles a HackathormEvent object
type HackathormEventReconciler struct {
	client.Client
	Log    logr.Logger
	Scheme *runtime.Scheme
}

//+kubebuilder:rbac:groups=event.hackathorm.org,resources=hackathormevents,verbs=get;list;watch;create;update;patch;delete
//+kubebuilder:rbac:groups=event.hackathorm.org,resources=hackathormevents/status,verbs=get;update;patch
//+kubebuilder:rbac:groups=event.hackathorm.org,resources=hackathormevents/finalizers,verbs=update
// todo rbac roles?

// Reconcile is part of the main kubernetes reconciliation loop which aims to
// move the current state of the cluster closer to the desired state.
// TODO(user): Modify the Reconcile function to compare the state specified by
// the HackathormEvent object against the actual cluster state, and then
// perform operations to make the cluster state reflect the state specified by
// the user.
//
// For more details, check Reconcile and its Result here:
// - https://pkg.go.dev/sigs.k8s.io/controller-runtime@v0.7.2/pkg/reconcile
func (r *HackathormEventReconciler) Reconcile(ctx context.Context, req ctrl.Request) (ctrl.Result, error) {
	log := r.Log.WithValues("hackathormevent", req.NamespacedName)
	log.Info("reconcile execution started", "req", req)

	// initialization (todo finish after initialization)
	event := &eventv1.HackathormEvent{}
	err := r.Client.Get(ctx, req.NamespacedName, event)
	if err != nil {
		log.Info("event not found")
		return ctrl.Result{}, client.IgnoreNotFound(err)
	}
	log.Info("got event", "event", event.Spec)

	// reload all participants in to state
	participants := &participantv1.HackathormParticipantList{}
	if err = r.List(ctx, participants, client.InNamespace(req.Namespace)); err != nil {
		log.Error(err, "unable to list participants")
		return ctrl.Result{}, err
	}
	log.Info("got participants", "length", len(participants.Items))
	for _, participant := range participants.Items {
		ref, err := reference.GetReference(r.Scheme, &participant)
		if err != nil {
			log.Error(err, "unable to get reference", "participant", participant)
			return ctrl.Result{Requeue: true, RequeueAfter: 10 * time.Second}, nil
		}
		event.Status.Participants = append(event.Status.Participants, *ref)
	}
	if err = r.Status().Update(ctx, event); err != nil {
		log.Error(err, "unable to update status of event")
		return ctrl.Result{}, err
	}

	log.Info("reconcile execution finished")
	return ctrl.Result{}, nil
}

// SetupWithManager sets up the controller with the Manager.
func (r *HackathormEventReconciler) SetupWithManager(mgr ctrl.Manager) error {
	return ctrl.NewControllerManagedBy(mgr).
		For(&eventv1.HackathormEvent{}).
		//Owns(&participantv1.HackathormParticipant{}).
		Watches(&source.Kind{Type: &participantv1.HackathormParticipant{}}, &handler.EnqueueRequestForOwner{}).
		Complete(r)
}
