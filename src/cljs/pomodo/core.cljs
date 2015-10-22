(ns pomodo.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [put! <! >! chan]]
            [cljs.pprint :refer [cl-format]]
            [clojure.string :as str]
            [sablono.core :as html :refer-macros [html]]
            [cljs-time.format :refer [date-formatters]]
            [cljs-time.core :as cljs-time]))

(def app-state (atom {:time {:minutes 3 :seconds 0}
                      :state false}))

(defn get-time []
  (:time (om/root-cursor app-state)))
(defn get-minutes []
  (:minutes (get-time)))
(defn get-seconds []
  (:seconds (get-time)))

(defn set-time! [minutes seconds]
  (let [cursor (om/ref-cursor (om/root-cursor app-state))]
    (om/update! cursor :time {:minutes minutes :seconds seconds})))

(defn start? []
  (:state (om/root-cursor app-state)))

(defn set-state! [state]
  (let [cursor (om/ref-cursor (om/root-cursor app-state))]
    (om/update! cursor :state state )))

(defn display-time []
  (let [min (get-minutes)
        sec (get-seconds)
        formatted (map #(if (<= % 0) "00" (cl-format nil "~2,'0d" %)) [min sec])]
    (str/join ":" formatted)))


(def timer-chan
  (let [c (chan)
        timer (js/setInterval
               (fn []
                 (when (start?)
                   (put! c true))) 1000)]
    c))

(defn expired? []
  (and (<= (get-minutes) 0) (<= (get-seconds) 0)))

(go (while true
      (<! timer-chan)
      (when-not (expired?)
        (let [min (get-minutes)
              sec (get-seconds)]
          (if (= 0 sec)
            (set-time! (dec min) 59)
            (set-time!  min (dec sec)))
          ;; check if we need to stop the timer
          (when (expired?) (set-state! false))))))


;; start countdown
#_(set-state! true)


(defn- application [data]
  (reify
      om/IRenderState
    (render-state [_ _]
      (html
       [:div.app [:h1 (display-time)]
        ]))))

(defn main []
  (om/root application app-state
           {:target (. js/document (getElementById "app"))}))
