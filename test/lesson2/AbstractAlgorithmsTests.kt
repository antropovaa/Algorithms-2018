package lesson2

import java.io.BufferedWriter
import java.io.File
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.test.assertEquals

abstract class AbstractAlgorithmsTests {

    private val minPrice = 42

    private val maxPrice = 99999

    private fun generatePrices(size: Int): Pair<Int, Int> {
        val random = Random()
        val prices = mutableListOf<Int>()
        for (index in 1..size) {
            val price = minPrice + 1 + random.nextInt(maxPrice - 1 - minPrice)
            prices += price
        }
        val firstIndex = random.nextInt(size)
        val secondIndex = random.nextInt(size).let {
            when (it) {
                firstIndex -> if (firstIndex == size - 1) firstIndex - 1 else firstIndex + 1
                else -> it
            }
        }
        val (minIndex, maxIndex) =
                if (firstIndex < secondIndex) firstIndex to secondIndex else secondIndex to firstIndex
        prices[minIndex] = minPrice
        prices[maxIndex] = maxPrice

        fun BufferedWriter.writePrices() {
            for (price in prices) {
                write(price.toString())
                newLine()
            }
            close()
        }

        File("temp_prices.txt").bufferedWriter().writePrices()
        return minIndex + 1 to maxIndex + 1
    }

    fun optimizeBuyAndSell(optimizeBuyAndSell: (String) -> Pair<Int, Int>) {
        assertEquals(3 to 4, optimizeBuyAndSell("input/buysell_in1.txt"))
        assertEquals(8 to 12, optimizeBuyAndSell("input/buysell_in2.txt"))
        assertEquals(3 to 4, optimizeBuyAndSell("input/buysell_in3.txt"))
        try {
            val expectedAnswer = generatePrices(1000)
            assertEquals(expectedAnswer, optimizeBuyAndSell("temp_prices.txt"))
        } finally {
            File("temp_prices.txt").delete()
        }
        try {
            val expectedAnswer = generatePrices(100000)
            assertEquals(expectedAnswer, optimizeBuyAndSell("temp_prices.txt"))
        } finally {
            File("temp_prices.txt").delete()
        }
    }

    fun josephTask(josephTask: (Int, Int) -> Int) {
        try {
            josephTask(0, 1)
        } catch (e: IllegalArgumentException) {
        }
        assertEquals(1, josephTask(1, 1))
        assertEquals(2, josephTask(2, 1))
        assertEquals(50000000, josephTask(50000000, 1))
        assertEquals(3, josephTask(8, 5))
        assertEquals(28, josephTask(40, 3))
        var menNumber = 2
        for (i in 1..20) {
            assertEquals(1, josephTask(menNumber, 2))
            menNumber *= 2
        }
    }

    fun longestCommonSubstring(longestCommonSubstring: (String, String) -> String) {
        assertEquals("", longestCommonSubstring("", ""))
        assertEquals("оз", longestCommonSubstring("роза", "озеро"))
        assertEquals("", longestCommonSubstring("РОЗА", "озеро"))
        assertEquals("", longestCommonSubstring("мой мир", "я"))
        assertEquals("зд", longestCommonSubstring("здравствуй мир", "мы здесь"))
        assertEquals("СЕРВАТОР", longestCommonSubstring("ОБСЕРВАТОРИЯ", "КОНСЕРВАТОРЫ"))
        assertEquals("огда ", longestCommonSubstring(
                """
Мой дядя самых честных правил,
Когда не в шутку занемог,
Он уважать себя заставил
И лучше выдумать не мог.
Его пример другим наука;
Но, боже мой, какая скука
С больным сидеть и день и ночь,
Не отходя ни шагу прочь!
Какое низкое коварство
Полуживого забавлять,
Ему подушки поправлять,
Печально подносить лекарство,
Вздыхать и думать про себя:
Когда же черт возьмет тебя!
                """.trimIndent(),
                """
Так думал молодой повеса,
Летя в пыли на почтовых,
Всевышней волею Зевеса
Наследник всех своих родных.
Друзья Людмилы и Руслана!
С героем моего романа
Без предисловий, сей же час
Позвольте познакомить вас:
Онегин, добрый мой приятель,
Родился на брегах Невы,
Где, может быть, родились вы
Или блистали, мой читатель;
Там некогда гулял и я:
Но вреден север для меня
                """.trimIndent()
        ))
        assertEquals("\n(с) Этот весь длинный-длинный текст является цитатой из Пушкина, поэма \"Руслан и Людмила\"\n",
                longestCommonSubstring(
                        """
Дела давно минувших дней,
Преданья старины глубокой.

В толпе могучих сыновей,
С друзьями, в гриднице высокой
Владимир-солнце пировал;
Меньшую дочь он выдавал
За князя храброго Руслана
И мед из тяжкого стакана
За их здоровье выпивал.
Не скоро ели предки наши,
Не скоро двигались кругом
Ковши, серебряные чаши
С кипящим пивом и вином.
Они веселье в сердце лили,
Шипела пена по краям,
Их важно чашники носили
И низко кланялись гостям.

Слилися речи в шум невнятный;
Жужжит гостей веселый круг;
Но вдруг раздался глас приятный
И звонких гуслей беглый звук;
Все смолкли, слушают Баяна:
И славит сладостный певец
Людмилу-прелесть, и Руслана,
И Лелем свитый им венец.

Но, страстью пылкой утомленный,
Не ест, не пьет Руслан влюбленный;
На друга милого глядит,
Вздыхает, сердится, горит
И, щипля ус от нетерпенья,
Считает каждые мгновенья.
В унынье, с пасмурным челом,
За шумным, свадебным столом
Сидят три витязя младые;
Безмолвны, за ковшом пустым,
Забыты кубки круговые,
И брашна неприятны им;
Не слышат вещего Баяна;
Потупили смущенный взгляд:
То три соперника Руслана;
В душе несчастные таят
Любви и ненависти яд.
Один — Рогдай, воитель смелый,
Мечом раздвинувший пределы
Богатых киевских полей;
Другой — Фарлаф, крикун надменный,
В пирах никем не побежденный,
Но воин скромный средь мечей;
Последний, полный страстной думы,
Младой хазарский хан Ратмир:
Все трое бледны и угрюмы,
И пир веселый им не в пир.

Вот кончен он; встают рядами,
Смешались шумными толпами,
И все глядят на молодых:
Невеста очи опустила,
Как будто сердцем приуныла,
И светел радостный жених.
Но тень объемлет всю природу,
Уж близко к полночи глухой;
Бояре, задремав от меду,
С поклоном убрались домой.
Жених в восторге, в упоенье:
Ласкает он в воображенье
Стыдливой девы красоту;
(с) Этот весь длинный-длинный текст является цитатой из Пушкина, поэма "Руслан и Людмила"
Но с тайным, грустным умиленьем
Великий князь благословеньем
Дарует юную чету.

И вот невесту молодую
Ведут на брачную постель;
Огни погасли... и ночную
Лампаду зажигает Лель.
Свершились милые надежды,
Любви готовятся дары;
Падут ревнивые одежды
На цареградские ковры...
Вы слышите ль влюбленный шепот,
И поцелуев сладкий звук,
И прерывающийся ропот
Последней робости?.. Супруг
Восторги чувствует заране;
И вот они настали... Вдруг
Гром грянул, свет блеснул в тумане,
Лампада гаснет, дым бежит,
Кругом всё смерклось, всё дрожит,
И замерла душа в Руслане...
Всё смолкло. В грозной тишине
Раздался дважды голос странный,
И кто-то в дымной глубине
Взвился чернее мглы туманной...
И снова терем пуст и тих;
Встает испуганный жених,
С лица катится пот остылый;
Трепеща, хладною рукой
Он вопрошает мрак немой...
О горе: нет подруги милой!
Хватает воздух он пустой;
Людмилы нет во тьме густой,
Похищена безвестной силой.

Ах, если мученик любви
Страдает страстью безнадежно,
Хоть грустно жить, друзья мои,
Однако жить еще возможно.

Но после долгих, долгих лет
Обнять влюбленную подругу,
Желаний, слез, тоски предмет,
И вдруг минутную супругу
Навек утратить... о друзья,
Конечно лучше б умер я!

Однако жив Руслан несчастный.
Но что сказал великий князь?
Сраженный вдруг молвой ужасной,
На зятя гневом распалясь,
Его и двор он созывает:
«Где, где Людмила?» — вопрошает
С ужасным, пламенным челом.
Руслан не слышит. «Дети, други!
Я помню прежние заслуги:
О, сжальтесь вы над стариком!
Скажите, кто из вас согласен
Скакать за дочерью моей?
Чей подвиг будет не напрасен,
Тому — терзайся, плачь, злодей!
Не мог сберечь жены своей! —
Тому я дам ее в супруги
С полцарством прадедов моих.
Кто ж вызовется, дети, други?..»
«Я!» — молвил горестный жених.
«Я! я!» — воскликнули с Рогдаем
Фарлаф и радостный Ратмир:
«Сейчас коней своих седлаем;
Мы рады весь изъездить мир.
Отец наш, не продлим разлуки;
Не бойся: едем за княжной».
И с благодарностью немой
В слезах к ним простирает руки
Старик, измученный тоской.

Все четверо выходят вместе;
Руслан уныньем как убит;
Мысль о потерянной невесте
Его терзает и мертвит.

Садятся на коней ретивых;
Вдоль берегов Днепра счастливых
Летят в клубящейся пыли;
Уже скрываются вдали;
Уж всадников не видно боле...
Но долго всё еще глядит
Великий князь в пустое поле
И думой им вослед летит.

Руслан томился молчаливо,
И смысл и память потеряв.
Через плечо глядя спесиво
И важно подбочась, Фарлаф,
Надувшись, ехал за Русланом.
Он говорит: «Насилу я
На волю вырвался, друзья!
Ну, скоро ль встречусь с великаном?
Уж то-то крови будет течь,
Уж то-то жертв любви ревнивой!
Повеселись, мой верный меч,
Повеселись, мой конь ретивый!»

Хазарский хан, в уме своем
Уже Людмилу обнимая,
Едва не пляшет над седлом;
В нем кровь играет молодая,
Огня надежды полон взор:
То скачет он во весь опор,
То дразнит бегуна лихого,
Кружит, подъемлет на дыбы
Иль дерзко мчит на холмы снова.

Рогдай угрюм, молчит — ни слова...
Страшась неведомой судьбы
И мучась ревностью напрасной,
Всех больше беспокоен он,
И часто взор его ужасный
На князя мрачно устремлен.

Соперники одной дорогой
Все вместе едут целый день.

Днепра стал темен брег отлогий;
С востока льется ночи тень;
Туманы над Днепром глубоким;
Пора коням их отдохнуть.
Вот под горой путем широким
Широкий пересекся путь.
«Разъедемся, пора! — сказали, —
Безвестной вверимся судьбе».
И каждый конь, не чуя стали,
По воле путь избрал себе.

Что делаешь, Руслан несчастный,
Один в пустынной тишине?
Людмилу, свадьбы день ужасный,
Всё, мнится, видел ты во сне.
На брови медный шлем надвинув,
Из мощных рук узду покинув,
Ты шагом едешь меж полей,
И медленно в душе твоей
Надежда гибнет, гаснет вера.

Но вдруг пред витязем пещера;
В пещере свет. Он прямо к ней
Идет под дремлющие своды,
Ровесники самой природы.
Вошел с уныньем: что же зрит?
В пещере старец; ясный вид,
Спокойный взор, брада седая;
Лампада перед ним горит;
За древней книгой он сидит,
Ее внимательно читая.
«Добро пожаловать, мой сын! —
Сказал с улыбкой он Руслану. —
Уж двадцать лет я здесь один
Во мраке старой жизни вяну;
Но наконец дождался дня,
Давно предвиденного мною.
Мы вместе сведены судьбою;
Садись и выслушай меня.
Руслан, лишился ты Людмилы;
Твой твердый дух теряет силы;

Но зла промчится быстрый миг:
На время рок тебя постиг.
С надеждой, верою веселой
Иди на всё, не унывай;
Вперед! мечом и грудью смелой
Свой путь на полночь пробивай.

Узнай, Руслан: твой оскорбитель
Волшебник страшный Черномор,
Красавиц давний похититель,
Полнощных обладатель гор.
Еще ничей в его обитель
Не проникал доныне взор;
Но ты, злых козней истребитель,
В нее ты вступишь, и злодей
Погибнет от руки твоей.
Тебе сказать не должен боле:
Судьба твоих грядущих дней,
Мой сын, в твоей отныне воле».

Наш витязь старцу пал к ногам
И в радости лобзает руку.
Светлеет мир его очам,
И сердце позабыло муку.
Вновь ожил он; и вдруг опять
На вспыхнувшем лице кручина...
«Ясна тоски твоей причина;
Но грусть не трудно разогнать, —
Сказал старик, — тебе ужасна
Любовь седого колдуна;
Спокойся, знай: она напрасна
И юной деве не страшна.
Он звезды сводит с небосклона,
Он свистнет — задрожит луна;
Но против времени закона
Его наука не сильна.
Ревнивый, трепетный хранитель
Замков безжалостных дверей,
Он только немощный мучитель
Прелестной пленницы своей.
                """.trimIndent(),
                        """
Вокруг нее он молча бродит,
Клянет жестокий жребий свой...
Но, добрый витязь, день проходит,
А нужен для тебя покой».

Руслан на мягкий мох ложится
Пред умирающим огнем;
Он ищет позабыться сном,
Вздыхает, медленно вертится...
Напрасно! Витязь наконец:
«Не спится что-то, мой отец!
Что делать: болен я душою,
И сон не в сон, как тошно жить.
Позволь мне сердце освежить
Твоей беседою святою.
Прости мне дерзостный вопрос.
Откройся: кто ты, благодатный,
Судьбы наперсник непонятный?
В пустыню кто тебя занес?»

Вздохнув с улыбкою печальной,
Старик в ответ: «Любезный сын,
Уж я забыл отчизны дальной
Угрюмый край. Природный финн,
В долинах, нам одним известных,
Гоняя стадо сел окрестных,
В беспечной юности я знал
Одни дремучие дубравы,
Ручьи, пещеры наших скал
Да дикой бедности забавы.
Но жить в отрадной тишине
Дано не долго было мне.

Тогда близ нашего селенья,
Как милый цвет уединенья,
Жила Наина. Меж подруг
Она гремела красотою.
Однажды утренней порою
Свои стада на темный луг
Я гнал, волынку надувая;
Передо мной шумел поток.

Одна, красавица младая
На берегу плела венок.
Меня влекла моя судьбина...
Ах, витязь, то была Наина!
Я к ней — и пламень роковой
За дерзкий взор мне был наградой,
И я любовь узнал душой
С ее небесною отрадой,
С ее мучительной тоской.

Умчалась года половина;
Я с трепетом открылся ей,
Сказал: люблю тебя, Наина.
Но робкой горести моей
Наина с гордостью внимала,
Лишь прелести свои любя,
И равнодушно отвечала:
«Пастух, я не люблю тебя!»

И всё мне дико, мрачно стало:
Родная куща, тень дубров,
Веселы игры пастухов —
Ничто тоски не утешало.
В уныньи сердце сохло, вяло.
И наконец задумал я
Оставить финские поля;
Морей неверные пучины
С дружиной братской переплыть
И бранной славой заслужить
Вниманье гордое Наины.
Я вызвал смелых рыбаков
Искать опасностей и злата.
Впервые тихий край отцов
Услышал бранный звук булата
И шум немирных челноков.
Я вдаль уплыл, надежды полный,
С толпой бесстрашных земляков;
Мы десять лет снега и волны
Багрили кровию врагов.
Молва неслась: цари чужбины
Страшились дерзости моей;

Их горделивые дружины
Бежали северных мечей.
Мы весело, мы грозно бились,
Делили дани и дары,
И с побежденными садились
За дружелюбные пиры.
Но сердце, полное Наиной,
Под шумом битвы и пиров,
Томилось тайною кручиной,
Искало финских берегов.
Пора домой, сказал я, други!
Повесим праздные кольчуги
Под сенью хижины родной.
Сказал — и весла зашумели;
И, страх оставя за собой,
В залив отчизны дорогой
Мы с гордой радостью влетели.

Сбылись давнишние мечты,
Сбылися пылкие желанья!
Минута сладкого свиданья,
И для меня блеснула ты!
К ногам красавицы надменной
Принес я меч окровавленный,
Кораллы, злато и жемчуг;
Пред нею, страстью упоенный,
Безмолвным роем окруженный
Ее завистливых подруг,
Стоял я пленником послушным;
Но дева скрылась от меня,
Примолвя с видом равнодушным:
«Герой, я не люблю тебя!»

К чему рассказывать, мой сын,
Чего пересказать нет силы?
Ах, и теперь один, один,
Душой уснув, в дверях могилы,
Я помню горесть, и порой,
Как о минувшем мысль родится,
По бороде моей седой
Слеза тяжелая катится.

Но слушай: в родине моей
Между пустынных рыбарей
Наука дивная таится.
Под кровом вечной тишины,
Среди лесов, в глуши далекой
Живут седые колдуны;
К предметам мудрости высокой
Все мысли их устремлены;
Все слышит голос их ужасный,
Что было и что будет вновь,
И грозной воле их подвластны
И гроб и самая любовь.

И я, любви искатель жадный,
Решился в грусти безотрадной
Наину чарами привлечь
И в гордом сердце девы хладной
Любовь волшебствами зажечь.
Спешил в объятия свободы,
В уединенный мрак лесов;
И там, в ученье колдунов,
Провел невидимые годы.
Настал давно желанный миг,
И тайну страшную природы
Я светлой мыслию постиг:
Узнал я силу заклинаньям.
Венец любви, венец желаньям!
Теперь, Наина, ты моя!
Победа наша, думал я.
Но в самом деле победитель
Был рок, упорный мой гонитель.

В мечтах надежды молодой,
В восторге пылкого желанья,
Творю поспешно заклинанья,
Зову духов — и в тьме лесной
Стрела промчалась громовая,
Волшебный вихорь поднял вой,
Земля вздрогнула под ногой...
И вдруг сидит передо мной
Старушка дряхлая, седая,

Глазами впалыми сверкая,
С горбом, с трясучей головой,
Печальной ветхости картина.
Ах, витязь, то была Наина!..
Я ужаснулся и молчал,
Глазами страшный призрак мерил,
В сомненье всё еще не верил
И вдруг заплакал, закричал:
«Возможно ль! ах, Наина, ты ли!
Наина, где твоя краса?
Скажи, ужели небеса
Тебя так страшно изменили?
Скажи, давно ль, оставя свет,
Расстался я с душой и с милой?
Давно ли?..» «Ровно сорок лет, —
Был девы роковой ответ, —
Сегодня семьдесят мне било.
Что делать, — мне пищит она, —
Толпою годы пролетели.
Прошла моя, твоя весна —
Мы оба постареть успели.
Но, друг, послушай: не беда
Неверной младости утрата.
Конечно, я теперь седа,
Немножко, может быть, горбата;
Не то, что в старину была,
Не так жива, не так мила;
Зато (прибавила болтунья)
Открою тайну: я колдунья!»

И было в самом деле так.
Немой, недвижный перед нею,
Я совершенный был дурак
Со всей премудростью моею.

Но вот ужасно: колдовство
Вполне свершилось по несчастью.
Мое седое божество
Ко мне пылало новой страстью.
Скривив улыбкой страшный рот,
Могильным голосом урод

Бормочет мне любви признанье.
Вообрази мое страданье!
Я трепетал, потупя взор;
Она сквозь кашель продолжала
Тяжелый, страстный разговор:
«Так, сердце я теперь узнала;
Я вижу, верный друг, оно
Для нежной страсти рождено;
Проснулись чувства, я сгораю,
Томлюсь желаньями любви...
Приди в объятия мои...
О милый, милый! умираю...»

И между тем она, Руслан,
Мигала томными глазами;
И между тем за мой кафтан
Держалась тощими руками;
И между тем — я обмирал,
От ужаса зажмуря очи;
И вдруг терпеть не стало мочи;
Я с криком вырвался, бежал.
(с) Этот весь длинный-длинный текст является цитатой из Пушкина, поэма "Руслан и Людмила"
Она вослед: «О, недостойный!
Ты возмутил мой век спокойный,
Невинной девы ясны дни!
Добился ты любви Наины,
И презираешь — вот мужчины!
Изменой дышат все они!
Увы, сама себя вини;
Он обольстил меня, несчастный!
Я отдалась любови страстной...
Изменник, изверг! о позор!
Но трепещи, девичий вор!»

Так мы расстались. С этих пор
Живу в моем уединенье
С разочарованной душой;
И в мире старцу утешенье
Природа, мудрость и покой.
Уже зовет меня могила;
Но чувства прежние свои
Еще старушка не забыла
                """.trimIndent()
                ))
    }

    fun calcPrimesNumber(calcPrimesNumber: (Int) -> Int) {
        assertEquals(0, calcPrimesNumber(-1))
        assertEquals(0, calcPrimesNumber(1))
        assertEquals(1, calcPrimesNumber(2))
        assertEquals(2, calcPrimesNumber(4))
        assertEquals(4, calcPrimesNumber(10))
        assertEquals(8, calcPrimesNumber(20))
        assertEquals(1000, calcPrimesNumber(7920))
        assertEquals(1229, calcPrimesNumber(10000))
        assertEquals(2262, calcPrimesNumber(20000))
        assertEquals(5133, calcPrimesNumber(50000))
        assertEquals(9592, calcPrimesNumber(100000))
        assertEquals(17984, calcPrimesNumber(200000))
        assertEquals(33860, calcPrimesNumber(400000))
        assertEquals(49098, calcPrimesNumber(600000))
        assertEquals(56543, calcPrimesNumber(700000))
        assertEquals(63951, calcPrimesNumber(800000))
        assertEquals(71274, calcPrimesNumber(900000))
        assertEquals(78498, calcPrimesNumber(1000000))
        assertEquals(148933, calcPrimesNumber(2000000))
        assertEquals(348513, calcPrimesNumber(5000000))
        assertEquals(664579, calcPrimesNumber(10000000))
    }

    fun baldaSearcher(baldaSearcher: (String, Set<String>) -> Set<String>) {
        assertEquals(setOf("ТРАВА", "КРАН", "АКВА", "НАРТЫ"),
                baldaSearcher("input/balda_in1.txt", setOf("ТРАВА", "КРАН", "АКВА", "НАРТЫ", "РАК")))
    }
}