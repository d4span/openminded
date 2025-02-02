(ns openminded.usecases.startup-test
  (:require [clojure.spec.test.alpha :as stest]
            [clojure.test :refer :all]
            [openminded.test-util :refer [check]]
            [openminded.usecases.startup]
            [openminded.usecases.startup-spec]))

;; (deftest startup-namespace
;;     (let [functions (stest/enumerate-namespace 'openminded.usecases.startup)
;;           [passed message] (check functions 32)]
;;       (is passed message)))
