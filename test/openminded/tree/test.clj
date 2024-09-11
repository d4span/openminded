(ns openminded.tree.test
  (:require [openminded.tree :as tree]
            [openminded.tree.spec :as spec]
            [openminded.test-util :as test-util]))

(test-util/test-function openminded.tree/is-tree? 1)
