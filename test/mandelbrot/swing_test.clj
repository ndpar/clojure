(ns mandelbrot.swing-test
  (:use clojure.test clojure.contrib.math mandelbrot.swing))

(deftest log-test
  (are [e a] (< (abs (- e a)) 0.01)
       1.000 (log 2 2)
       2.000 (log 2 4)
       1.000 (log 10 10)))

(deftest log-scale-test
  (are [e a] (< (abs (- e a)) 0.01)
       0.000 (log-scale 0 1024) ; log_{255} 0 = -Infinity
       0.000 (log-scale 4 1024) ; 256 * 4 / 1024 = 1; log_{255} 1 = 0
       0.125 (log-scale 8 1024)
       0.250 (log-scale 16 1024)
       0.375 (log-scale 32 1024)
       0.500 (log-scale 64 1024)
       0.625 (log-scale 128 1024)
       0.750 (log-scale 256 1024)
       0.875 (log-scale 512 1024)
       1.000 (log-scale 1024 1024)))

