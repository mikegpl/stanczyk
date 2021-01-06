package agh.sm.exchange;

import stanczyk.Stanczyk;

public class KnowledgeStore {

    public Stanczyk.DevicesKnowledge getLocalKnowledge() {
        return Stanczyk.DevicesKnowledge.newBuilder().build(); // todo mikegpl - implement storing and retrieving
    }
}
