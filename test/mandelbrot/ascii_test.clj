(ns mandelbrot.ascii-test
  (:refer-clojure :exclude [+ *])
  (:use clojure.test mandelbrot.ascii
        (clojure.contrib complex-numbers)
        (clojure.contrib.generic [arithmetic :only [+ *]]
                                 [math-functions :only [abs]])))

(deftest *-test
  (is (= (complex -5 -5)
         (* (complex -2 1) (complex 1 3)))))

(deftest abs-test
  (is (= 5.0 (abs (complex -3 4))))
  (is (= 13.0 (abs (complex 5 12))))
  (is (< (- 1.802 (abs (complex 1.0 1.5))) 0.01)))

(deftest mandelbrot-seq-test
  (is (= [(complex -2 1) (complex 1 -3) (complex -10 -5) (complex 73 101)]
         (take 4 (mandelbrot-seq -2 1)))))

(deftest m-range-test
  (let [rx (m-range -2 1 50)]
    (is (= 50 (count rx)))
    (is (= [-2 -97/50 -47/25 -91/50 -44/25] (take 5 rx)))))

(deftest char-test
  (is (= " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~"
         (apply str (map char (range 32 127))))))

(deftest mandelbrot-char-test
  (testing "Graph is symmetrical to real axis"
    (is (= \~ (mandelbrot-char -2.0 1.5) (mandelbrot-char -2.0 -1.5)))
    (is (= \} (mandelbrot-char 1.0 1.5) (mandelbrot-char 1.0 -1.5)))))

(defn test-mandelbrot-seq []
  (take 10 (mandelbrot-seq 1.0 1.0))
  (take 10 (mandelbrot-seq 4.0 1.0))
  (take 10 (mandelbrot-seq 0.5 0.5))
  (take 10 (mandelbrot-seq 0.4 0.4))
  (take 10 (mandelbrot-seq -0.2 -0.2))
  (take 10 (mandelbrot-seq -0.2 -0.12)))

