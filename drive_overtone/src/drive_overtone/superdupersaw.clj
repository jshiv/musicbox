(ns drive-overtone.supersaw)
(use 'overtone.live)
(use 'overtone.inst.piano)
(use 'overtone.inst.synth)
(use 'overtone.synths)

(* 8 80)
(dark-sea-horns :freq 640 :amp 0.1)
(dark-sea-horns :freq 200 :amp 0.1)
(dark-sea-horns :freq 120 :amp 0.1)
(dark-sea-horns :freq 80 :amp 0.2)
(stop)
(fallout-wind)
(odoc fallout-wind)
(hz->midi 80)


(inst-volume! dark-sea-horns 0.1)

()
(kill dark-sea-horns)
(demo (supersaws))
(definst supersaws [freq 440 dur 0.2 release 0.5 amp 5.6 cutoff 3500 env-amount 0.5]
         (let [snd-fn (fn [freq]
                        (let [tune (ranged-rand 0.99 1.01)]
                          (-> (lf-saw (* freq tune))
                              (delay-c 0.005 (ranged-rand 0.0001 0.01)))))
               hi-saws (splay (repeatedly 5 #(snd-fn freq)))
               lo-saws (splay (repeatedly 5 #(snd-fn (/ freq 2))))
               noise (pink-noise)
               snd (+ (* 0.65 hi-saws) (* 0.85 lo-saws) (* 0.12 noise))
               env (env-gen (adsr 0.001 0.7 0.2 0.1) (line:kr 1 0 (+ dur release)) :action FREE)]
           (-> snd
               (clip2 0.45)
               (rlpf (+ freq (env-gen (adsr 0.001) (line:kr 1 0 dur) :level-scale cutoff)) 0.75)
               (free-verb :room 1.8 :mix 0.45)
               (* env amp)
               pan2)))

(event-debug-off)

;; Based on Dawn by Schemawound: http://sccode.org/1-c
(defsynth fallout-wind [decay 30 attack 30 out-bus 0]
  (let [lfo  (+ 0.5 (* 0.5 (sin-osc:kr [(ranged-rand 0.5 1000) (ranged-rand 0.5 1000)] :phase (* 1.5 Math/PI))))
        lfo3 (+ 0.5 (* 0.5 (sin-osc:kr [(ranged-rand 0.1 0.5) (ranged-rand 0.1 0.5)] :phase (* 1.5 Math/PI))))
        lfo2 (+ 0.5 (* 0.5 (sin-osc:kr [(* (ranged-rand 0.5 1000) lfo lfo3) (* (ranged-rand 0.5 1000) (- 1 lfo) (- 1 lfo3))] :phase (* 1.5 Math/PI))))
        fillers (map (fn [_] (* lfo2 (sin-osc:ar (ranged-rand 40 1000) :phase 0))) (range 0 100))]
    (out:ar out-bus  (* (mix:ar fillers)
                  (env-gen:kr (perc attack decay) :action FREE)))))



(defsynth touch-wood [note 60 out-bus 0 amp 5]
  (let [freq (midicps note)
        src (bpf:ar (* (white-noise:ar) (line:kr 5 0 0.02)) freq 0.02)
        amp amp]
    (out:ar out-bus (* amp (pan2:ar src)))))

(touch-wood)
(fallout-wind :out-bus 1)

(midi->hz 63 )
(println "hey")

(on-event [:midi :note-on]
                (fn [e]
                  (let [note  (:note e)
                     vel  (:velocity-f e)]
                         (supersaws :freq (midi->hz note))))
                  ::keyboard-handler)
(stop)
((demo saw 800.0))
(demo (saw 440.0))
(kill supersaw)
(demo (supersaw :freq 440 :dur 2 :amp 1))
