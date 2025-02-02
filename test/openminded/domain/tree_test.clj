(ns openminded.domain.tree-test
  (:require
   [clojure.spec.test.alpha :as stest]
   [clojure.test :refer :all]
   [openminded.test-util :refer [check]]
   [openminded.domain.tree-spec]
   [openminded.domain.tree]))

(deftest tree-namespace
  (testing "the tree namespace"
    (let [functions (stest/enumerate-namespace 'openminded.domain.tree)
          [passed message] (check functions 32)]
      (is passed message))))
