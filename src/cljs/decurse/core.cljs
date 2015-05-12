(ns decurse.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defonce app-state (atom {:text "Hello Chestnut!"}))

(defn render-header []
  (dom/div #js {:className "page-header-content"}
           (dom/h1 nil (str "decurse"))))

(defn render-paragraphs
  [paragraphs]
  (map #(dom/p nil (om/value %)) paragraphs))

(def introduction-paragraphs
  ["Article introduction."
   "I can reload this on the fly using Figwheel."
   "When I add data to this and save, the DOM updates automatically without needing to refresh the browser."])

(def how-it-works-paragraphs
  ["MAGIC!"
   "More Magic"])

(defn render-body []
  (dom/div nil
           (dom/h2 nil (om/value "Introduction"))
           (apply dom/div nil (render-paragraphs introduction-paragraphs))
           (dom/h2 nil (om/value "How it works"))
           (apply dom/div nil (render-paragraphs how-it-works-paragraphs))))

(defn main []
  (om/root
   (fn [app owner]
     (reify
       om/IRender
       (render [_]
         (dom/div nil
                  (render-header)
                  (render-body)))))
   app-state
   {:target (. js/document (getElementById "app"))}))
