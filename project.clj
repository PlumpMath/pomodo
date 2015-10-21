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
                 [figwheel "0.4.1"]
                 [environ "1.0.1"]]

  :source-paths ["src/clj"]

  :plugins [[lein-figwheel "0.4.1" :exclusions [org.clojure/core.cache]]
            [lein-cljsbuild "1.0.6"]
            [lein-environ "1.0.1"]]

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

  :main pomodo.server)
