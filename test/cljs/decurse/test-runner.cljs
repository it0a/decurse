(ns decurse.test-runner
  (:require
   [cljs.test :refer-macros [run-tests]]
   [decurse.core-test]))

(enable-console-print!)

(defn runner []
  (if (cljs.test/successful?
       (run-tests
        'decurse.core-test))
    0
    1))
