package com.alexchernikov.flow.reactivestreams;

public class ShoutingStage {

    /**
     *
     * - Review the participant classes and interfaces
     * - Rules:
     * 		- Any number of listeners can be subscribed to a Shouter.
     * 		- a Shouter does not keep historical messages. Listeners receive only new messages.
     * 		- The assumption is that a subscribed listener wish to receive any new message that the shouter sends.
     * 		- in order to receive a message, the Listener should explicitly request it from the Shouter (it is not enough to just be subscribed)
     * 		- a listener can be muted, it means that it should stop receiving new messages from the Shouter
     * 		- a listener can be un-muted, it means that it should continue receiving new messages from the Shouter
     *
     * Expected output:
     *
     * Listener 1 successfully subscribed
     * Shouting: ImportantMessage{id=0, message='a very important message'}
     * Listener 1: ImportantMessage{id=0, message='a very important message'}
     * Shouting: ImportantMessage{id=1, message='a very important message'}
     * Listener 1: ImportantMessage{id=1, message='a very important message'}
     * Shouting: ImportantMessage{id=2, message='a very important message'}
     * Listener 1: ImportantMessage{id=2, message='a very important message'}
     * Shouting: ImportantMessage{id=3, message='a very important message'}
     * Listener 1: ImportantMessage{id=3, message='a very important message'}
     * Shouting: ImportantMessage{id=4, message='a very important message'}
     * Listener 1: ImportantMessage{id=4, message='a very important message'}
     * Shouting: ImportantMessage{id=5, message='a very important message'}
     * Shouting: ImportantMessage{id=6, message='a very important message'}
     * Shouting: ImportantMessage{id=7, message='a very important message'}
     * Shouting: ImportantMessage{id=8, message='a very important message'}
     * Listener 1: ImportantMessage{id=8, message='a very important message'}
     * Shouting: ImportantMessage{id=9, message='a very important message'}
     * Listener 1: ImportantMessage{id=9, message='a very important message'}
     * Shouter complete
     * Listener 1 Completed
     */
    public static void main(String[] args) throws InterruptedException {
        MessageShouter shouter = new MessageShouter();
        MessageListener listener = new MessageListener(1);
        shouter.subscribe(listener);

        for (int i = 0; i < 10; i++) {
            shouter.shout(new ImportantMessage(i, "a very important message"));
            Thread.sleep(100);

            if (i == 4) {
                listener.mute();
            }
            if (i == 7) {
                listener.unmute();
            }
        }

        shouter.close();
    }

}