(ns pomodo.test-runner
  (:require
   [cljs.test :refer-macros [run-tests]]
   [pomodo.core-test]))

(enable-console-print!)

(defn runner []
  (if (cljs.test/successful?
       (run-tests
        'pomodo.core-test))
    0
    1))
