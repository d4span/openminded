(ns openminded.main-test
  (:require [clojure.spec.test.alpha :as stest]
            [clojure.test :refer]
            [openminded.main :refer]
            [openminded.test-util :refer [check]]))

;; (deftest main-namespace
;;   (let [functions (stest/enumerate-namespace 'openminded.main)
;;         [passed message] (check functions 32)]
;;     (is (or (empty? functions) passed) message)))
