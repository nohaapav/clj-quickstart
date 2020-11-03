(defproject server "1.0"
  :description "Clojure web application server template"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.logging "0.4.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [http-kit "2.2.0"]
                 [metosin/reitit "0.3.10"]]
  :min-lein-version "2.8.2"
  :plugins [[lein-ring "0.12.5"]]
  :source-paths ["src"]
  :test-paths ["test"]
  :clean-targets ^{:protect false} [:target-path]
  :uberjar-name "server.jar"
  :main app.server
  :ring {:handler app.server/app}
  :repl-options {:init-ns repl.user}

  :profiles {:dev     {:source-paths ["src" "dev"]}
             :prod    {:source-paths ["src"]
                       :prep-tasks   ["javac" "compile"]
                       :omit-source  true
                       :aot          :all}
             :uberjar [:prod]})