(ns electron.core
  (:require [electron.menu :as menu]))

(enable-console-print!)

(def app            (js/require "app"))
(def browser-window (js/require "browser-window"))

(.start (js/require "crash-reporter"))

(def main-window (atom nil))

(defn window-all-closed []
  (when-not (= js/process.platform "darwin")
    (.quit app)))

(defn app-ready []
  (reset! main-window (browser-window.
                       (clj->js {:width 400, :height 300})))
  ;; Initial Menu
  (menu/menu-init)

  (.loadUrl @main-window (str "file://" js/__dirname "/index.html"))
  ;;(.openDevTools @main-window)

  (.on @main-window "closed"
       #(reset! main-window nil)))

;; main
(.log js/console "start pomodo application....")

;; event listener
(.on app "ready" app-ready)
(.on app "window-all-closed" window-all-closed)