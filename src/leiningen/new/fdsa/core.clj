(ns {{name}}.core
  (:use plumbing.core)
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [{{name}}.nodes.lifecycle :as lifecycle]
            [{{name}}.nodes.root-node :as root-node]
            [taoensso.timbre :as timbre
             :refer (trace debug info warn error fatal spy with-log-level)]))

(def default-config {})

(defn deep-merge
  "Recursively merges maps. If keys are not maps, the last value wins.
   Taken from pedestal demo"
  [& vals]
  (if (every? map? vals)
    (apply merge-with deep-merge vals)
    (last vals)))

(defn fetch-config
  "Here are configs that we use:
   - the one in this file
   - the one provided to this function (optional)
   The latter takes precedence over former ones"
  ([] (fetch-config {}))
  ([top-config]
     (let [config (deep-merge default-config top-config)]
       (clojure.pprint/pprint
        {:default default-config
         :top top-config
         :merge config})
       config)))

(defn prepare-system []
  (root-node/prepare))

(defn run-system []
  (let [sys (prepare-system)]
    (lifecycle/start-node! sys (fetch-config) {})))
