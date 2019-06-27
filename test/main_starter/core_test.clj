(ns main-starter.core-test
  (:require [clojure.test :refer :all]
            [main-starter.core :refer :all]))

(deftest a-test
  (testing "1 is 1."
    (is (= 1 1)))
  (testing "2 is not 1."
    (is (not (= 1 2)))))
