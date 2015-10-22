(defproject pomodo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.145"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.omcljs/om "0.9.0"]
                 [sablono "0.3.6"]
                 [com.andrewmcveigh/cljs-time "0.3.14"]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [compojure "1.4.0"]
                 [enlive "1.1.6"]
                 [environ "1.0.1"]]

  :source-paths ["src/clj"]

  :plugins [[lein-cljsbuild "1.0.6"]
            [lein-environ "1.0.1"]
            [lein-figwheel "0.4.1" :exclusions [org.clojure/core.cache]]]

  :min-lein-version "2.5.0"

  :hooks [leiningen.cljsbuild]

  :cljsbuild {:builds
              {:app {:source-paths ["src/cljs/"]
                     :compiler {:output-to     "resources/public/js/app.js"
                                :output-dir    "resources/public/js/out"
                                :source-map    "resources/public/js/out.js.map"
                                :preamble      ["react/react.min.js"]
                                :optimizations :none
                                :pretty-print  true}}
               ;; TODO: advanced build
               :ui {:source-paths ["src/cljs"]
                    :compiler {:output-to     "app/js/app.js"
                               :preamble      ["react/react.min.js"]
                               :optimizations :whitespace
                               :pretty-print  true}}
               :main {:source-paths ["src/electron"]
                      :compiler {:output-to     "app/main.js"
                                 :optimizations :simple
                                 :pretty-print  true}}
               }}

  :profiles {:dev {:source-paths ["env/dev/clj"]
                   :test-paths ["test/clj"]

                   :dependencies [[figwheel "0.4.1"]
                                  [figwheel-sidecar "0.4.1"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.11"]
                                  [weasel "0.7.0"]]

                   :repl-options {:init-ns pomodo.server
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                   :plugins [[lein-figwheel "0.4.1"]]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :css-dirs ["resources/public/css"]}

                   :env {:is-dev true}

                   :cljsbuild {;;:test-commands { "test" ["phantomjs" "env/test/js/unit-test.js" "env/test/unit-test.html"] }
                               :builds {:app {:source-paths ["env/dev/cljs"]}
                                        :test {:source-paths ["src/cljs" "test/cljs"]
                                               :compiler {:output-to     "resources/public/js/app_test.js"
                                                          :output-dir    "resources/public/js/test"
                                                          :source-map    "resources/public/js/test.js.map"
                                                          :optimizations :whitespace
                                                          :pretty-print  false}}}}}}

  :main pomodo.server)
