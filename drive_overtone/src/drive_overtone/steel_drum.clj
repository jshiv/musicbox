(use 'overtone.live)
(use 'overtone.inst.piano)
(use 'overtone.inst.synth)



(definst steel-drum [note 60 amp 0.8]
    (let [freq (midicps note)]
          (* amp
                    (env-gen (perc 0.01 0.2) 1 1 0 1 :action FREE)
                           (+ (sin-osc (/ freq 2))
                                        (rlpf (saw freq) (* 1.1 freq) 0.4)))))



(def player (midi-poly-player steel-drum))



(demo 7 (lpf (mix (saw [50 (line 100 1600 5) 101 100.5]))
                               (lin-lin (lf-tri (line 2 20 5)) -1 1 400 4000)))
