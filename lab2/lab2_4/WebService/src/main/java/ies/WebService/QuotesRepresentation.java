package ies.WebService;

import java.util.List;

public record QuotesRepresentation(long id, String movie, List<String> quotes) { }
