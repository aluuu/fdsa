(ns leiningen.new.fdsa
  (:require [leiningen.new.templates :refer [renderer year project-name
                                             ->files sanitize-ns name-to-path
                                             multi-segment]]
            [leiningen.core.main :as main]))

(defn fdsa
  "An application project template."
  [name]
  (let [render (renderer "fdsa")
        main-ns (multi-segment (sanitize-ns name))
        data {:raw-name name
              :name (project-name name)
              :namespace main-ns
              :fs-name (clojure.string/replace (project-name name) #"-" "_")
              :nested-dirs (name-to-path main-ns)
              :year (year)}]
    (main/info "Generating a project called" name "based on the 'fdsa' template.")
    (->files data
             ["project.clj" (render "project.clj" data)]
             [".gitignore" (render "gitignore" data)]
             ["dev/user.clj" (render "user.clj" data)]
             ["src/clj/{{fs-name}}/core.clj" (render "core.clj" data)]
             ["src/clj/{{fs-name}}/nodes/lifecycle.clj" (render "lifecycle.clj" data)]
             ["src/clj/{{fs-name}}/nodes/root_node.clj" (render "root_node.clj" data)]
             "resources/public")))
