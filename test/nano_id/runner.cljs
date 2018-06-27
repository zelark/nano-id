(ns nano-id.runner
  (:require [nano-id.core-test]
            [nano-id.custom]
            [doo.runner :refer-macros [doo-tests]]))

(doo-tests
  'nano-id.core-test
  'nano-id.custom)
