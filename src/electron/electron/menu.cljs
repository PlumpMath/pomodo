(ns electron.menu)

(def template
  (clj->js
   [{:label "Pomodo"
     :submenu
     [{:label "Quit"
       :accelerator "Command+Q"
       :click (fn [] (.quit (js/require "app")))}]
     }
    ])
  )

(defn menu-init
  []
  (let [m (js/require "menu")
        menu (.buildFromTemplate m template)]
    (.setApplicationMenu m menu)))
