(ns {{name}}.nodes.root-node
  (:require
   [{{name}}.nodes.lifecycle :as lifecycle]
   [taoensso.timbre :as timbre
    :refer (trace debug info warn error fatal spy with-log-level)]))

(defrecord RootNode []
  lifecycle/Node
  (start [s config system]
    {:node s
     :system-additions {}
     :children-fns []})
  (stop [s]
    s))

(defn prepare []
  (->RootNode))
