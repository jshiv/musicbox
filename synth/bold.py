from pyo import *
pm_get_input_devices()

# Set Up Server
s = Server()
s.setMidiInputDevice(0) # Must be called before s.boot()
s.boot()
s.start()

midi = Notein() # The defaults are more than adequate


bend = Bendin(brange=2, scale=1)

ctl = Midictl(1, minscale=0, maxscale=1)



pitch = MToF(midi['pitch'] * bend)





amp = MidiAdsr(midi['velocity'], attack=.01, decay=0.05, sustain=0.7, release=0.1)

# wave = PartialTable()
wave = AtanTable()
# wave = ExpTable()
# wave = SquareTable()
# wave = CurveTable()
# wave = HannTable()
# wave = ChebyTable()
# wave = SincTable()
# wave = WinTable()

osc = Osc(wave, freq=pitch, mul=amp)
# osc.out()

verb = Freeverb(osc)#.out()

ctl = Midictl(1, minscale=0, maxscale=5)
chorus = Chorus(verb, depth=ctl, bal=.5)
# lfo = Sine(freq=.5, phase=.5, mul=.5, add=.3)
# delay = Delay(effect, delay=.5, feedback=lfo, maxdelay=5).out()
# delay = Delay(effect, delay=.5, feedback=.7, maxdelay=2).out()
delay = Delay(chorus, delay=0, feedback=.5, maxdelay=1)
delay.out()

# distro = Disto(effect, drive=.5).out()
# verb = Beat(osc).out()



s.gui(locals())
verb.ctrl()
wave.view()
