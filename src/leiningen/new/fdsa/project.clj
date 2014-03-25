(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [prismatic/plumbing "0.2.2"]
                 [com.taoensso/timbre "3.1.6"]]

  :main ^:skip-aot {{namespace}}

  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]
                                  [org.clojure/java.classpath "0.2.0"]]
                   :source-paths ["dev" "src/clj"]
                   :main ^:skip-aot user
                   ;; :aot ^:replace [{{name}}.types]
                   :jvm-opts ["-Xmx256m" "-Xms128m"] }})
