;; Clojure Mandelbrot generator
;; by Brian Carper
;; http://briancarper.net/blog/more-clojure-mandelbrot-goodness

(ns mandelbrot.swing
  (:use [clojure.contrib.seq :only (indexed)])
  (:import (javax.swing JFrame JPanel)
           (java.awt Color Dimension)
           (java.awt.image BufferedImage)))

(defn color-for-point [x0 y0 max-iterations color-fn]
  (let [x0 (float x0)
        y0 (float y0)]
    (loop [x x0, y y0, n 0]
      (if (= n max-iterations)
        (Color. 0 0 0)
        (let [new-x (+ x0 (- (* x x) (* y y))) 
              new-y (+ y0 (* 2 x y))
              zn (+ (* new-x new-x) (* new-y new-y))]
          (if (< zn 4)
            (recur new-x new-y (inc n))
            (color-fn n zn max-iterations)))))))

;; These work OK for certain values of max-iterations
;; But it starts to look a bit dark around 2048

(defn log [base x]
  (/ (Math/log (float x)) (Math/log (float base))))

(defn log-scale [n max-n]
  (max (log 255 (* n (/ 255.0 max-n)))
       0.0))

(defn banded-color [n _ max-iterations]
  (let [b (log-scale n max-iterations)]
    (Color/getHSBColor 0.0 0.0 b)))

(defn smooth-color [n zn max-iterations]
  (let [s (->> 4 (log zn) (log 2))
        b (log-scale (+ n s) max-iterations)]
    (Color/getHSBColor 0.0 0.0 b)))

(defn- m-range [min max num-steps]
  (range min max (/ (- max min) (float num-steps))))

(def make-image
  (memoize
    (fn [mb]
      (let [img (BufferedImage. (inc (:width mb))
                                (inc (:height mb))
                                BufferedImage/TYPE_INT_ARGB)
            max-iterations (:max-iterations mb)
            color-fn (:color-fn mb)]
        (doseq [[x a] (indexed (m-range (:rmin mb) (:rmax mb) (:width mb)))
                [y b] (indexed (m-range (:imin mb) (:imax mb) (:height mb)))]
          (let [c (color-for-point a b max-iterations color-fn)]
            (.setRGB img x y (.getRGB c))))
        img))))

(defn mandelbrot
  "Given a hash of parameters, makes a Mandelbrot image and displays it.

  Required: rmin, rmax, imin, imax
  Optional: height, width (dimensions for the image)
  max-iterations (higher = better resolution but slower)
  color-fn (fn implementing pixel-coloring strategy)"
  [mb]
  (let [mb (merge {:height 600
                   :width 600
                   :max-iterations 128
                   :color-fn banded-color}
                  mb)
        frame (JFrame. "Mandelbrot")
        panel (proxy [JPanel] []
                (getPreferredSize [] (Dimension. (:width mb) (:height mb)))
                (paintComponent
                  [g]
                  (.drawImage g (make-image mb) 0 0 nil)))
        ]
    (doto frame
      (.setResizable false)
      (.add panel)
      (.pack)
      (.setVisible true))))

(defn save-to-file [img format filename]
  (javax.imageio.ImageIO/write img format filename))

;; Examples
;; (use 'mandelbrot.swing)

#_(mandelbrot {:rmin -2.25 :rmax 0.75 :imin -1.5 :imax 1.5})

;; This takes about 10 seconds to render on my old AMD dual-core
#_(mandelbrot {:rmin -2.25
             :rmax 0.75
             :imin -1.5
             :imax 1.5
             :width 900
             :height 900
             :max-iterations 1024
             :color-fn smooth-color})

;; Save to a PNG file
#_(save-to-file (make-image {:rmin -2.25 :rmax 0.75 :imin -1.5 :imax 1.5})
              "png"
              "mandelbrot.png")

