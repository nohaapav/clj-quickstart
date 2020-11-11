(defproject web "1.0"
  :description "Clojurescript web template with reitit routing & rum"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.597"]
                 [org.clojure/core.async "1.3.610"]
                 [cljsjs/react "16.8.6-0"]
                 [cljsjs/react-dom "16.8.6-0"]
                 [metosin/reitit "0.3.10"]
                 [rum "0.11.4"]]
  :min-lein-version "2.8.2"
  :source-paths ["src"]
  :resource-paths ["resources"]

  :plugins [[lein-shell "0.5.0"]]

  :aliases {"fig"         ["trampoline" "run" "-m" "figwheel.main"]
            "fig:dev"     ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "cljs-clean!" ["do"
                           ["shell" "rm" "-rf" "resources/public/js/out"]
                           ["shell" "rm" "-rf" "resources/public/js/web.js"]]
            "cljs-build!" ["run" "-m" "figwheel.main" "-O" "advanced" "-co" "min.cljs.edn" "-c"]}

  :profiles {:dev {:dependencies [[com.bhauman/figwheel-main "0.2.12"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]
                                  [cider/piggieback "0.4.1"]]
                   :source-paths ["dev"]
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]
                                  :init-ns          repl.user}}})