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

package v1

import (
	v1 "k8s.io/api/core/v1"
	metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
)

// EDIT THIS FILE!  THIS IS SCAFFOLDING FOR YOU TO OWN!
// NOTE: json tags are required.  Any new fields you add must have json tags for the fields to be serialized.

// HackathormEventSpec defines the desired state of HackathormEvent
type HackathormEventSpec struct {
	// INSERT ADDITIONAL SPEC FIELDS - desired state of cluster
	// Important: Run "make" to regenerate code after modifying this file

	// Foo is an example field of HackathormEvent. Edit hackathormevent_types.go to remove/update
	Foo string `json:"foo,omitempty"`
}

// HackathormEventStatus defines the observed state of HackathormEvent
type HackathormEventStatus struct {
	// INSERT ADDITIONAL STATUS FIELD - define observed state of cluster
	// Important: Run "make" to regenerate code after modifying this file

	// +optional
	Participants []v1.ObjectReference `json:"participants"`
}

//+kubebuilder:object:root=true
//+kubebuilder:subresource:status

// HackathormEvent is the Schema for the hackathormevents API
type HackathormEvent struct {
	metav1.TypeMeta   `json:",inline"`
	metav1.ObjectMeta `json:"metadata,omitempty"`

	Spec   HackathormEventSpec   `json:"spec,omitempty"`
	Status HackathormEventStatus `json:"status,omitempty"`
}

//+kubebuilder:object:root=true

// HackathormEventList contains a list of HackathormEvent
type HackathormEventList struct {
	metav1.TypeMeta `json:",inline"`
	metav1.ListMeta `json:"metadata,omitempty"`
	Items           []HackathormEvent `json:"items"`
}

func init() {
	SchemeBuilder.Register(&HackathormEvent{}, &HackathormEventList{})
}
