(defproject server "1.0"
  :description "Clojure web application server template"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.logging "0.4.1"]
                 [ring "1.6.3"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.3.1"]
                 [bk/ring-gzip "0.3.0"]
                 [http-kit "2.2.0"]
                 [expound "0.7.2"]
                 [metosin/reitit "0.3.10"]
                 [com.fasterxml.jackson.core/jackson-core "2.10.0"]
                 [com.fasterxml.jackson.core/jackson-databind "2.10.0"]]
  :min-lein-version "2.8.2"
  :plugins [[lein-ring "0.9.7"]]
  :source-paths ["src"]
  :test-paths ["test"]
  :clean-targets ^{:protect false} [:target-path]
  :uberjar-name "server.jar"
  :ring {:handler app.server/app :port 8080}
  :repl-options {:init-ns repl.user}

  :profiles {:dev     {:dependencies [[org.clojure/tools.nrepl "0.2.12"]]
                       :source-paths ["src" "dev"]}
             :prod    {:source-paths ["src"]
                       :prep-tasks   ["javac" "compile"]
                       :omit-source  true
                       :aot          :all}
             :uberjar [:prod]})