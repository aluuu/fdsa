(ns {{name}}.nodes.lifecycle
  (:require [taoensso.timbre :as timbre
             :refer (trace debug info warn error fatal spy with-log-level)]))

(defprotocol Node
  (start [_ config system])
  (stop [_]))

;; TODO: this will not work if "start" fails
;; TODO: call "stop" on nodes if something throws exception
(defn start-node! [node config system]
  (debug "Starting node" node)
  (let [{:keys [node system-additions children-fns]} (start node config system)
        system-ext (merge system system-additions)
        children-seq (for [cf children-fns] (start-node! (cf) config system-ext))
        children (vec children-seq)]
    {:node node
     :children children}))

(defn stop-node! [{:keys [node children]}]
  (debug "Stopping node" node)
  (let [children (->> children (map stop-node!) vec)
        node (stop node)]
    {:node node
     :children children}))

(defn restart-node! [node config system]
  (-> (stop-node! node)
      (:node)
      (start-node! config system)))
