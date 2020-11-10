(defproject webapp "1.0"
  :description "Clojure web application server serving react app"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.597"]
                 [org.clojure/tools.logging "0.4.1"]
                 [org.clojure/core.async "1.3.610"]
                 [metosin/reitit "0.3.10"]
                 [http-kit "2.4.0"]
                 [enlive "1.1.6"]
                 [ring "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [bk/ring-gzip "0.3.0"]
                 [cljsjs/react "16.8.6-0"]
                 [cljsjs/react-dom "16.8.6-0"]
                 [reagent "0.10.0"]
                 [re-frame "0.10.2"]
                 [cljs-ajax "0.8.1"]
                 [day8.re-frame/http-fx "0.2.1"]
                 [com.fasterxml.jackson.core/jackson-core "2.10.0"]
                 [com.fasterxml.jackson.core/jackson-databind "2.10.0"]
                 [org.eclipse.jetty/jetty-server "9.4.28.v20200408"]
                 [org.eclipse.jetty.websocket/websocket-servlet "9.4.28.v20200408"]
                 [org.eclipse.jetty.websocket/websocket-server "9.4.28.v20200408"]]
  :min-lein-version "2.8.2"
  :source-paths ["src/clj" "src/cljs" "src/cljc"]
  :test-paths ["test/clj" "test/cljc"]
  :resource-paths ["resources"]
  :main webapp.server

  :plugins [[lein-shell "0.5.0"]]

  :aliases {"fig"      ["trampoline" "run" "-m" "figwheel.main"]
            "fig:dev"  ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:min"  ["run" "-m" "figwheel.main" "-O" "advanced" "-co" "min.cljs.edn" "-c"]
            "clean!"   ["do"
                        ["shell" "rm" "-rf" "target"]
                        ["shell" "rm" "-rf" "resources/public/js/out"]
                        ["shell" "rm" "resources/public/js/webapp.js"]]
            "release!" ["do"
                        ["fig:min"]
                        ["uberjar"]]}

  :profiles {:dev     {:dependencies [[com.bhauman/figwheel-main "0.2.12"]
                                      [com.bhauman/rebel-readline-cljs "0.1.4"]
                                      [cider/piggieback "0.4.1"]]
                       :source-paths ["dev"]
                       :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]
                                      :init-ns          repl.user
                                      :port             56600}}
             :prod    {:source-paths ^:replace ["src/clj" "src/cljc"]
                       :uberjar-name "webapp.jar"
                       :prep-tasks   ["javac" "compile"]
                       :omit-source  true
                       :aot          :all}
             :uberjar [:prod]})