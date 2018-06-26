(ns nano-id.core-test
  (:require
    #?(:clj  [clojure.test :refer :all]
       :cljs [cljs.test :refer-macros [deftest is testing]])
    [nano-id.core :refer [nano-id]]))


(defn valid-id? [id]
  (re-matches #"^[A-Za-z0-9_~]+$" id))


(deftest a-test
  (testing "generagets URL-friendly IDs"
    (dotimes [n 10]
      (let [id (nano-id)]
        (is (== (count id) 21) "test default length")
        (is (= (valid-id? id) id) "test id alphabet")))))
