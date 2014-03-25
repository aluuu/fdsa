(ns user
  (:require
   [clojure.pprint :refer [pprint]]
   [clojure.tools.namespace.repl :as ctnrepl]
   [{{name}}.core :as core]
   [{{name}}.nodes.lifecycle :as lifecycle]))

(def dev-config {})

(defonce sys nil)

(defn start []
  (alter-var-root
   #'sys
   (fn [_]
     (lifecycle/start-node!
      (core/prepare-system)
      (core/fetch-config dev-config)
      {}))))

(defn stop []
  (alter-var-root #'sys (fn [sys] (lifecycle/stop-node! sys))))

(defn restart []
  (alter-var-root
   #'sys
   (fn [sys]
     (lifecycle/restart-node!
      sys
      (core/fetch-config dev-config)
      {}))))

(defn refresh []
  (stop)
  (ctnrepl/refresh :after 'user/start)
  :ok)
