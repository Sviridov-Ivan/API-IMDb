package com.example.apiimdb.data.dto.names

import com.example.apiimdb.data.dto.Response

class NamesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<PersonDto>
) : Response()