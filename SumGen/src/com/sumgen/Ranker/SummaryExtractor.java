package com.sumgen.Ranker;

public interface SummaryExtractor {

    /**
     * @return Sentence[] the summary
     * @see Sentence
     */
    public Sentence[] getSummary();
}