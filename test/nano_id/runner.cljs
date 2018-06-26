(ns nano-id.runner
  (:require [nano-id.core-test]
            [doo.runner :refer-macros [doo-tests]]))

(doo-tests 'nano-id.core-test)
