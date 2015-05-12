(ns decurse.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defonce app-state (atom {:text "Hello Chestnut!"}))

(defn header []
  (dom/div nil
           (dom/h1 nil (str "decurse"))))

(defn render-paragraphs
  [paragraphs]
  (map #(dom/p nil (str %) paragraphs)))

(defn body []
  (dom/div nil
           (render-paragraphs ["hello" "goodbye"])
           (dom/button #js {:className "btn btn--positive"} (str "hello"))))

(defn main []
  (om/root
   (fn [app owner]
     (reify
       om/IRender
       (render [_]
         (dom/div nil
                  (header)
                  (body)))))
   app-state
   {:target (. js/document (getElementById "app"))}))
