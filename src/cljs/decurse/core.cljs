(ns decurse.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defonce app-state (atom {:location ""
                          :menu [["Pages" "/pages"]
                                 ["Images" "/images/"]]}))

(defn menu-item-view [parent-cursor item owner]
  (reify
    om/IRender
    (render [this]
      (dom/li #js {:className (if (= (:location @app-state) (last item)) "active" "inactive")}
                   (dom/a #js
                          {:onClick (fn [_] (swap! app-state assoc :location (last @item)))}
                          (first item))))))

(defn menu-view [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/li #js {:className "has-dropdown not-click"}
              (dom/a nil "Menu")
              (apply dom/ul #js {:className "dropdown"}
                     (om/build-all (partial menu-item-view app)
                                   (:menu app)))))))

(defn render-header []
  (dom/div #js {:className "page-header-content"}
           (dom/h1 nil (str "decurse"))))

(om/root menu-view app-state
           {:target (. js/document (getElementById "menu"))})

(defn render-paragraphs
  [paragraphs]
  (map #(dom/p nil (om/value %)) paragraphs))

(def article-one
  ["This is article one."])

(def article-two
  ["This is article two."])

(def introduction-paragraphs
  ["Article introduction."
   "I can reload this on the fly using Figwheel."
   "Hello Tay! <img src=''>"
   "This is pretty cool"
   "When I add data to this and save, the DOM updates automatically without needing to refresh the browser."])

(def how-it-works-paragraphs
  ["MAGIC!"
   "More Magic"])

(defn render-body []
  (dom/div nil
           (dom/h2 nil (om/value "Introduction"))
           (apply dom/div nil (render-paragraphs introduction-paragraphs))
           (dom/h2 nil (om/value "How it works"))
           (apply dom/div nil (render-paragraphs how-it-works-paragraphs))
           (dom/h2 nil (om/value "Article One"))
           (apply dom/div nil (render-paragraphs article-one))
           (dom/h2 nil (om/value "Article Two"))
           (apply dom/div nil (render-paragraphs article-two))))

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
