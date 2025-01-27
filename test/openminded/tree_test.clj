(ns openminded.tree-test
  (:require
   [clojure.spec.test.alpha :as stest]
   [clojure.test :refer :all]
   [openminded.test-util :refer [check]]
   [openminded.tree]))

(deftest tree-namespace
  (testing "the tree namespace"
    (let [functions (stest/enumerate-namespace 'openminded.tree)
          [passed message] (check functions 32)]
      (is passed message))))
