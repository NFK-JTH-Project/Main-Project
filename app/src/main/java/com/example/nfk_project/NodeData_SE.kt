package com.example.nfk_project

import NodeData

class NodeData_SE{

    var positionNodes = "B;E1017;Rummet är det första till vänster.\n" +
            "B;E1022;Rummet är det andra på vänster sida.\n" +
            "B;E1020;Området ligger i mitten av korridoren.\n" +
            "B;E1028;Rummet är det sista på vänster sida.\n" +
            "B;STORA_ENSO;Rummet är i slutet av korridoren.\n" +
            "C;E1105;Rummet kommer ligga till höger i korridoren.\n" +
            "C;E11XX;Rummet ligger i den första korridoren till vänster.\n" +
            "D;E1206;Rummet är det första till vänster.\n" +
            "D;E13XX;Rummet ligger i den tredje vingen.\n" +
            "F;E1219;Rummet är det första på höger sida.\n" +
            "F;E1217;Rummet ligger på höger sida, längre ner i korridoren.\n" +
            "F;E12XX;Rummet ligger i den andra vingen. \n" +
            "E;E1405;Rummet ligger på vänster sida i huvudkorridoren.\n" +
            "E;FAGERHULTSAULAN;Rummet ligger på höger sida i huvudkorridoren.\n" +
            "E;E14XX;Rummet ligger i den fjärde vingen.\n" +
            "G;E1419;Rummet ligger längst in till höger i den fjärde vingen.\n" +
            "G;E1418;Rummet ligger längst in till höger i den fjärde vingen.\n" +
            "A1;E2105;Rummet är det första till höger i huvudkorridoren.\n" +
            "A1;E2127;Rummet är det första till vänster i den första vingen.\n" +
            "A1;E21XX;Rummet ligger i den första korridoren till höger.\n" +
            "B1;E2205;Rummet är det första till vänster i huvudkorridoren\n" +
            "B1;E22XX;Rummet ligger i den andra korridoren till höger.\n" +
            "C1;E2303;Rummet ligger till vänster i huvudkorridoren.\n" +
            "B1;E2326;Rummet är det första på höger sida i korridoren.\n" +
            "B1;E23XX;Rummet ligger i korridoren till höger.\n" +
            "D1;E2404;Rummet ligger i slutet av huvudkorridoren på vänster sida.\n" +
            "E1;E2433;Rummet är det första till höger.\n" +
            "E1;E2432;Rummet är det andra till höger.\n" +
            "E1;E24XX;Rummet ligger i den vänstra korridoren.\n" +
            "F1;E2426;Rummet ligger på höger sida.\n" +
            "A2;LEONARDO;RUmmet är det första till höger i huvudkorridoren.\n" +
            "A2;E31XX;Rummet ligger i korridoren till vänster i den första vingen.\n" +
            "B2;E3205;Rummet är det första till vänster i huvudkorridoren.\n" +
            "B2;E3231;Rummet är det första till höger i korridoren i den andra vingen.\n" +
            "B2;E32XX;Rummet ligger till höger i korridoren i den andra vingen.\n" +
            "C2;E3303;Rummet ligger till vänster i huvudkorridoren.\n" +
            "C2;E33XX;Rummet ligger i korridoren till höger.\n" +
            "D2;E3404;Rummet ligger till vänster i huvudkorridoren.\n" +
            "E2;E3439;Rummet är det första på höger sida.\n" +
            "E2;E3437;Rummet ligger på höger sida, längre ner i korridoren.\n" +
            "E2;E34XX;Rummet ligger i korridoren till vänster.\n" +
            "A3;DA_VINCI;Rummet är det första till höger i huvudkorridoren.\n" +
            "A3;GALLILEO;Rummet ligger på höger sida, längre ner i huvudkorridoren.\n" +
            "A3;E41XX;Rummet ligger i korridoren till vänster i den första vingen.\n" +
            "B3;E4205;Rummet är det första till vänster i huvudkorridoren.\n" +
            "B3;E42XX;Rummet ligger i korridoren till höger i den andra vingen.\n" +
            "C3;E4304;Rummet ligger till vänster i huvudkorridoren.\n" +
            "C3;E43XX;Rummet ligger i korridoren till höger.\n" +
            "D3;E4404;Rummet ligger till vänster i huvudkorridoren.\n" +
            "D3;E44XX;Rummet ligger till höger om huvudkorridoren."

    var connections: String = "A B C D A1 B1 A2 B2 A3 B3\n" +
            "B A\n" +
            "C A\n" +
            "D F E\n" +
            "F D\n" +
            "E D G\n" +
            "G E\n" +
            "A1 A\n" +
            "B1 A C1 D1\n" +
            "C1 B1\n" +
            "D1 C1 B1 E1\n" +
            "E1 D1 F1\n" +
            "F1 E1\n" +
            "A2 A\n" +
            "B2 A C2 D2\n" +
            "C2 B2\n" +
            "D2 B2 E2\n" +
            "E2 D2\n" +
            "A3 A\n" +
            "B3 A C3 D3\n" +
            "C3 B3\n" +
            "D3 B3"

    var directionNodes = "A;Du är här.\n" +
            "B;Ta den första korridoren innanför entrén.\n" +
            "C;Gå förbi trappan intill dig och sväng höger.\n" +
            "D;Gå förbi trappan intill dig och sväng vänster.\n" +
            "E;Följ korridoren hela vägen.\n" +
            "F;Gå in i den första korridoren till höger.\n" +
            "G;Sväng höger i slutet av korridoren.\n" +
            "A1;Ta trappan/hissen intill dig till den andra våningen och sväng höger.\n" +
            "B1;Ta trappan/hissen intill dig till den andra våningen och sväng vänster.\n" +
            "C1;Gå till den tredje vingen.\n" +
            "D1;Gå till den fjärde vingen i slutet av huvudkorridoren.\n" +
            "E1;Gå in i korridoren till höger.\n" +
            "F1;Gå igenom dörrarna till slutet av korridoren.\n" +
            "A2;Ta trappan/hissen intill dig till den tredje våningen och sväng höger.\n" +
            "B2;Ta trappan/hissen intill dig till den tredje våningen och sväng vänster.\n" +
            "C2;Gå till den tredje vingen.\n" +
            "D2;Gå till den fjärde vingen.\n" +
            "E2;Gå in i korridoren till höger.\n" +
            "A3;Ta trappan/hissen intill dig till den fjärde våningen och sväng höger.\n" +
            "B3;Ta trappan/hissen intill dig till den fjärde våningen och sväng vänster.\n" +
            "C3;Gå till den tredje vingen.\n" +
            "D3;Gå till den fjärde vingen i slutet av huvudkorridoren."
}