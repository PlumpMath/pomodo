(ns pomodo.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [sablono.core :as html :refer-macros [html]]))

(defonce app-state (atom {:msg "test"
                          :connected false}))

(defn- application [data]
  (reify
      om/IDidMount
    (did-mount [_]
      (.addEventListener js/document "deviceready" #(om/update! data :connected true)))
    om/IRenderState
    (render-state [_ _]
      (html
       [:div.app
        [:h1 (:msg data)]
        [:div#deviceready.blink
         (if (:connected data)
           [:p.event.received {:style {:display "block"}} "Device is Ready"]
           [:p.event.listening "Connecting to Device"])]
        ]))))

(defn main []
  (om/root application app-state
           {:target (. js/document (getElementById "app"))}))
