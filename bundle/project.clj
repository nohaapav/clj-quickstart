(defproject webapp "1.0"
  :description "Clojure web application server serving react app"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.597"]
                 [org.clojure/tools.logging "0.4.1"]
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
                 [com.fasterxml.jackson.core/jackson-databind "2.10.0"]]
  :min-lein-version "2.8.2"
  :plugins [[lein-cljsbuild "1.1.7"]]
  :source-paths ["src/clj" "src/cljs" "src/cljc"]
  :test-paths ["test/clj" "test/cljc"]
  :clean-targets ^{:protect false} ["resources/public/js/out"
                                    "resources/public/js/webapp.js"
                                    :target-path]
  :uberjar-name "webapp.jar"
  :main webapp.server
  :repl-options {:init-ns repl.user}
  :cljsbuild {:builds
              [{:id           "dev"
                :source-paths ["src/cljs" "src/cljc"]

                ;; The presence of a :figwheel configuration here
                ;; will cause figwheel to inject the figwheel client
                ;; into your build
                :figwheel     true
                :compiler     {:main                 webapp.app
                               :asset-path           "/js/out"
                               :output-to            "resources/public/js/webapp.js"
                               :output-dir           "resources/public/js/out"
                               :infer-externs        true
                               :parallel-build       true
                               :source-map-timestamp true
                               ;; To console.log CLJS data-structures make sure you enable devtools in Chrome
                               ;; https://github.com/binaryage/cljs-devtools
                               :preloads             [devtools.preload]}}

               ;; This next build is a compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id           "min"
                :source-paths ["src/cljs" "src/cljc"]
                :compiler     {:main                 webapp.app
                               :output-to            "resources/public/js/webapp.js"
                               :output-dir           "target"
                               :parallel-build       true
                               :infer-externs        true
                               :source-map-timestamp true
                               :optimizations        :advanced
                               :closure-defines      {"goog.DEBUG" false}
                               :pretty-print         false
                               :pseudo-names         false}}]}
  :figwheel {:http-server-root "public-assets"
             :css-dirs         ["resources/public/css"]
             :ring-handler     repl.user/http-handler
             :server-logfile   "log/figwheel.log"}

  ;; Setting up nREPL for Figwheel and ClojureScript dev
  ;; Please see:
  ;; https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-within-NRepl
  :profiles {:dev     {:dependencies [[figwheel "0.5.17"]
                                      [figwheel-sidecar "0.5.17"]
                                      [binaryage/devtools "0.9.10"]
                                      [cider/piggieback "0.4.1"]]
                       :plugins      [[lein-figwheel "0.5.17"]
                                      [lein-doo "0.1.6"]]
                       :source-paths ["dev"]
                       :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}
             :prod    {:source-paths ^:replace ["src/clj" "src/cljc"]
                       :prep-tasks   ["javac" "compile" ["cljsbuild" "once" "min"]]
                       :omit-source  true
                       :aot          :all}
             :uberjar [:prod]})