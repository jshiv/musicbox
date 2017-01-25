(use 'overtone.live)
(use 'overtone.inst.piano)
(use 'overtone.inst.synth)



(on-event [:midi :note-on]
                (fn [e]
                  (let [note (:note e)
                     vel  (:velocity e)]
                        (piano note 1 vel)))
                  ::keyboard-handler)


(demo 7 (lpf (mix (saw [50 (line 100 1600 5) 101 100.5]))
                               (lin-lin (lf-tri (line 2 20 5)) -1 1 400 4000)))
