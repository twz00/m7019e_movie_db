package com.ltu.m7019e.moviedb.v24.database

import com.ltu.m7019e.moviedb.v24.model.Genre
import com.ltu.m7019e.moviedb.v24.model.Movie

class Movies {
    fun getMovies(): List<Movie> {
        return listOf<Movie>(
            Movie(
                1,
                "Raya and the Last Dragon",
                "/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg",
                "/9xeEGUZjgiKlI69jwIOi0hjKUIk.jpg",
                "2021-03-03",
                arrayListOf(
                    Genre(16, "Animation"),
                    Genre(10751, "Family"),
                    Genre(14, "Fantasy"),
                    Genre(28, "Action"),
                    Genre(12, "Adventure")
                ),
                "Long ago, in the fantasy world of Kumandra, humans and dragons lived together in harmony. But when an evil force threatened the land, the dragons sacrificed themselves to save humanity. Now, 500 years later, that same evil has returned and it’s up to a lone warrior, Raya, to track down the legendary last dragon to restore the fractured land and its divided people."
            ),
            Movie(
                2,
                "Sentinelle",
                "/fFRq98cW9lTo6di2o4lK1qUAWaN.jpg",
                "/6TPZSJ06OEXeelx1U1VIAt0j9Ry.jpg",
                "2021-03-05",
                arrayListOf(
                    Genre(28, "Action"),
                    Genre(35, "Comedy"),
                    Genre(80, "Crime")
                ),
                "Transferred home after a traumatizing combat mission, a highly trained French soldier uses her lethal skills to hunt down the man who hurt her sister."
            ),
            Movie(
                3,
                "Zack Snyder's Justice League",
                "/tnAuB8q5vv7Ax9UAEje5Xi4BXik.jpg",
                "/pcDc2WJAYGJTTvRSEIpRZwM3Ola.jpg",
                "2021-03-18",
                arrayListOf(
                    Genre(28, "Action"),
                    Genre(14, "Fantasy"),
                    Genre(12, "Adventure")
                ),
                "Determined to ensure Superman's ultimate sacrifice was not in vain, Bruce Wayne aligns forces with Diana Prince with plans to recruit a team of metahumans to protect the world from an approaching threat of catastrophic proportions."
            ),
            Movie(
                4,
                "Tom & Jerry",
                "/6KErczPBROQty7QoIsaa6wJYXZi.jpg",
                "/z7HLq35df6ZpRxdMAE0qE3Ge4SJ.jpg",
                "2021-02-11",
                arrayListOf(
                    Genre(35, "Comedy"),
                    Genre(16, "Animation"),
                    Genre(10751, "Family"),
                ),
                "Tom the cat and Jerry the mouse get kicked out of their home and relocate to a fancy New York hotel, where a scrappy employee named Kayla will lose her job if she can’t evict Jerry before a high-class wedding at the hotel. Her solution? Hiring Tom to get rid of the pesky mouse."
            ),
            Movie(
                5,
                "Below Zero",
                "/dWSnsAGTfc8U27bWsy2RfwZs0Bs.jpg",
                "/srYya1ZlI97Au4jUYAktDe3avyA.jpg",
                "2021-01-29",
                arrayListOf(
                    Genre(28, "Action"),
                    Genre(80, "Crime"),
                    Genre(53, "Thriller"),
                ),
                "When a prisoner transfer van is attacked, the cop in charge must fight those inside and outside while dealing with a silent foe: the icy temperatures."
            ),
            Movie(
                1,
                "Raya and the Last Dragon",
                "/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg",
                "/9xeEGUZjgiKlI69jwIOi0hjKUIk.jpg",
                "2021-03-03",
                arrayListOf(
                    Genre(16, "Animation"),
                    Genre(10751, "Family"),
                    Genre(14, "Fantasy"),
                    Genre(28, "Action"),
                    Genre(12, "Adventure")
                ),
                "Long ago, in the fantasy world of Kumandra, humans and dragons lived together in harmony. But when an evil force threatened the land, the dragons sacrificed themselves to save humanity. Now, 500 years later, that same evil has returned and it’s up to a lone warrior, Raya, to track down the legendary last dragon to restore the fractured land and its divided people."
            ),
            Movie(
                2,
                "Sentinelle",
                "/fFRq98cW9lTo6di2o4lK1qUAWaN.jpg",
                "/6TPZSJ06OEXeelx1U1VIAt0j9Ry.jpg",
                "2021-03-05",
                arrayListOf(
                    Genre(28, "Action"),
                    Genre(35, "Comedy"),
                    Genre(80, "Crime")
                ),
                "Transferred home after a traumatizing combat mission, a highly trained French soldier uses her lethal skills to hunt down the man who hurt her sister."
            ),
            Movie(
                3,
                "Zack Snyder's Justice League",
                "/tnAuB8q5vv7Ax9UAEje5Xi4BXik.jpg",
                "/pcDc2WJAYGJTTvRSEIpRZwM3Ola.jpg",
                "2021-03-18",
                arrayListOf(
                    Genre(28, "Action"),
                    Genre(14, "Fantasy"),
                    Genre(12, "Adventure")
                ),
                "Determined to ensure Superman's ultimate sacrifice was not in vain, Bruce Wayne aligns forces with Diana Prince with plans to recruit a team of metahumans to protect the world from an approaching threat of catastrophic proportions."
            ),
            Movie(
                4,
                "Tom & Jerry",
                "/6KErczPBROQty7QoIsaa6wJYXZi.jpg",
                "/z7HLq35df6ZpRxdMAE0qE3Ge4SJ.jpg",
                "2021-02-11",
                arrayListOf(
                    Genre(35, "Comedy"),
                    Genre(16, "Animation"),
                    Genre(10751, "Family"),
                ),
                "Tom the cat and Jerry the mouse get kicked out of their home and relocate to a fancy New York hotel, where a scrappy employee named Kayla will lose her job if she can’t evict Jerry before a high-class wedding at the hotel. Her solution? Hiring Tom to get rid of the pesky mouse."
            ),
            Movie(
                5,
                "Below Zero",
                "/dWSnsAGTfc8U27bWsy2RfwZs0Bs.jpg",
                "/srYya1ZlI97Au4jUYAktDe3avyA.jpg",
                "2021-01-29",
                arrayListOf(
                    Genre(28, "Action"),
                    Genre(80, "Crime"),
                    Genre(53, "Thriller"),
                ),
                "When a prisoner transfer van is attacked, the cop in charge must fight those inside and outside while dealing with a silent foe: the icy temperatures."
            ),
        )
    }
}