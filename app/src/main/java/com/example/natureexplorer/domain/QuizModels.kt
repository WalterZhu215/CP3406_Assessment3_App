package com.example.natureexplorer.domain

data class QuizQuestion(
    val question: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)

object QuizEngine {

    fun isAnswerCorrect(
        question: QuizQuestion,
        selectedAnswerIndex: Int
    ): Boolean {

        return selectedAnswerIndex ==
                question.correctAnswerIndex
    }

    fun calculateScore(
        questions: List<QuizQuestion>,
        selectedAnswers: List<Int>
    ): Int {

        var score = 0

        questions.forEachIndexed { index, question ->

            val selectedAnswer =
                selectedAnswers.getOrNull(index)

            if (
                selectedAnswer ==
                question.correctAnswerIndex
            ) {

                score++
            }
        }

        return score
    }

    fun calculatePercentage(
        score: Int,
        totalQuestions: Int
    ): Int {

        if (totalQuestions == 0) {
            return 0
        }

        return (score * 100) / totalQuestions
    }
}

object QuizQuestionBank {

    fun getQuestions(
        trailName: String,
        difficulty: String,
        isEnglish: Boolean
    ): List<QuizQuestion> {

        val easyQuestions = listOf(

            QuizQuestion(
                question =
                    if (isEnglish)
                        "Why should hikers stay on designated paths?"
                    else
                        "徒步者为什么应该留在指定路径上？",

                answers =
                    if (isEnglish)
                        listOf(
                            "To protect plants and habitats",
                            "To make the trail longer",
                            "To attract more wildlife"
                        )
                    else
                        listOf(
                            "保护植物和栖息地",
                            "让路线变得更长",
                            "吸引更多野生动物"
                        ),

                correctAnswerIndex = 0
            ),

            QuizQuestion(
                question =
                    if (isEnglish)
                        "Which action follows Leave No Trace principles?"
                    else
                        "以下哪种行为符合无痕山林原则？",

                answers =
                    if (isEnglish)
                        listOf(
                            "Carry your rubbish out",
                            "Feed wild animals",
                            "Pick native flowers"
                        )
                    else
                        listOf(
                            "把自己的垃圾带走",
                            "给野生动物喂食",
                            "采摘本地花卉"
                        ),

                correctAnswerIndex = 0
            ),

            QuizQuestion(
                question =
                    if (isEnglish)
                        "What does biodiversity mean?"
                    else
                        "生物多样性是什么意思？",

                answers =
                    if (isEnglish)
                        listOf(
                            "The variety of living things",
                            "Only the number of trees",
                            "The temperature of an ecosystem"
                        )
                    else
                        listOf(
                            "生物种类的多样性",
                            "只有树木的数量",
                            "生态系统的温度"
                        ),

                correctAnswerIndex = 0
            )
        )


        val mediumQuestions = listOf(

            QuizQuestion(
                question =
                    if (isEnglish)
                        "How can invasive species affect an ecosystem?"
                    else
                        "入侵物种会怎样影响生态系统？",

                answers =
                    if (isEnglish)
                        listOf(
                            "They can compete with native species",
                            "They always improve biodiversity",
                            "They only affect the weather"
                        )
                    else
                        listOf(
                            "它们可能与本地物种竞争",
                            "它们一定会增加生物多样性",
                            "它们只会影响天气"
                        ),

                correctAnswerIndex = 0
            ),

            QuizQuestion(
                question =
                    if (isEnglish)
                        "What is the safest way to observe wildlife at $trailName?"
                    else
                        "在 $trailName 观察野生动物最安全的方式是什么？",

                answers =
                    if (isEnglish)
                        listOf(
                            "Observe quietly from a distance",
                            "Walk very close to the animal",
                            "Give the animal food"
                        )
                    else
                        listOf(
                            "保持距离并安静观察",
                            "尽量靠近动物",
                            "给动物喂食"
                        ),

                correctAnswerIndex = 0
            )
        )


        val hardQuestions = listOf(

            QuizQuestion(
                question =
                    if (isEnglish)
                        "What does carrying capacity describe in an ecosystem?"
                    else
                        "生态系统中的环境承载力描述什么？",

                answers =
                    if (isEnglish)
                        listOf(
                            "The population an environment can sustainably support",
                            "The maximum length of a hiking trail",
                            "The number of visitors in one day"
                        )
                    else
                        listOf(
                            "环境能够持续支持的种群数量",
                            "徒步路线的最大长度",
                            "一天中的游客数量"
                        ),

                correctAnswerIndex = 0
            ),

            QuizQuestion(
                question =
                    if (isEnglish)
                        "A major reduction in pollinators would most directly affect:"
                    else
                        "传粉动物大量减少最直接会影响：",

                answers =
                    if (isEnglish)
                        listOf(
                            "Plant reproduction",
                            "Rock formation",
                            "Wind speed"
                        )
                    else
                        listOf(
                            "植物繁殖",
                            "岩石形成",
                            "风速"
                        ),

                correctAnswerIndex = 0
            )
        )


        return when (difficulty) {

            "Easy" -> {
                easyQuestions
            }

            "Hard" -> {
                easyQuestions +
                        mediumQuestions +
                        hardQuestions
            }

            else -> {
                easyQuestions +
                        mediumQuestions
            }
        }
    }
}

