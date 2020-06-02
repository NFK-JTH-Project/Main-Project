class NodeData(){
    var positionNodes: String = "B;E1017;The room is the first one on the left.\n" +
            "B;E1022;The room is the second one on the left.\n" +
            "B;E1020;The area is in the middle of the hallway.\n" +
            "B;E1028;The room is the last one on the left side.\n" +
            "B;STORA_ENSO;The room is at the end of the hallway.\n" +
            "B;E1029; The room is at the end of the hallway.\n" +
            "C;E1105;The room will be on the right side.\n" +
            "C;E11XX;The room will be in the first hallway to the left.\n" +
            "D;E1206;The room will be the first one on the left.\n" +
            "D;E13XX;The room will be in the second corridor on the right.\n" +
            "F;E1219;The room will be the first one on the right.\n" +
            "F;E1217;The room will be the on the right side further down the hall.\n" +
            "F;E12XX;The room will be in the hallway.\n" +
            "E;GJUTERISALEN;The room will be on the left side.\n" +
            "E;FAGERHULTSAULAN;The room will be on the right side.\n" +
            "E;E14XX;The room should be on the left or right side.\n" +
            "G;E1419;The room will be in the far right corner.\n" +
            "G;E1418;The room will be in the far right corner.\n" +
            "A1;E2105;The room will be the first one on the right in the main corridor.\n" +
            "A1;E2127;The room will be the first one on the left in the first wing.\n" +
            "A1;E21XX;The room will in the corridor to the left in the first wing.\n" +
            "B1;E2205;The room will be the first one on the left in the main corridor.\n" +
            "B1;E22XX;The room will be in the second wing on the right.\n" +
            "C1;E2303;The room is located on the left in the main corridor.\n" +
            "B1;E2326;The room will be the first one on the right in the right corridor.\n" +
            "B1;E23XX;The room is located in the right corridor.\n" +
            "D1;E2404;The room is located at the end of the main corridor on the left.\n" +
            "E1;E2433;The room will be the first one on the right.\n" +
            "E1;E2432;The room will be the second one on the right.\n" +
            "E1;E24XX;The room will be in the left corridor.\n" +
            "F1;E2426;The room will be on the right side.\n" +
            "A2;LEONARDO;The room will be the first one on the right in the main corridor.\n" +
            "A2;E31XX;The room will be in the corridor to the left in the first wing.\n" +
            "B2;E3205;The room will the first one on the left in the main corridor.\n" +
            "B2;E3231;The room will be the first one to the right in the second wing.\n" +
            "B2;E32XX;The room will be in the corridor to the right in the second wing.\n" +
            "C2;E3303;The room is located on the left in the main corridor.\n" +
            "C2;E33XX;The room will be in the right corridor.\n" +
            "D2;E3404;The room is located on the left in the main corridor.\n" +
            "E2;E3439;The room will be the first one on the right side.\n" +
            "E2;E3437;The room is located on the right side further down the hall.\n" +
            "E2;E34XX;The room is located in the left corridor.\n" +
            "A3;DA_VINCI;The room will be the first one on the right in the main corridor.\n" +
            "A3;GALLILEO;The room is located on the right side in further down the main corridor.\n" +
            "A3;E41XX;The room will be in the corridor to the left in the first wing.\n" +
            "B3;E4205;The room will be the first one on the left in the main corridor.\n" +
            "B3;E42XX;The room will be in the corridor to the right in the second wing.\n" +
            "C3;E4304;The room is located on the left in the main corridor.\n" +
            "C3;E43XX;The room will be in the right corridor.\n" +
            "D3;E4404;The room is located on the left in the main corridor.\n" +
            "D3;E44XX;The room is located in the area to the right of the main corridor."

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
    var directionNodes: String = "A;You are here.\n" +
            "B;Take the corridor to the left.\n" +
            "C;Go past the staircase in front of you, and take a right.\n" +
            "D;Go past the staircase next to you and take a left.\n" +
            "E;Follow the corridor all the way down.\n" +
            "F;Go into the first corridor on the right side.\n" +
            "G;Take a right at the end of the hallway.\n" +
            "A1;Take the elevator/staircase in front of you to the second floor and take a right.\n" +
            "B1;Take the elevator/staircase in front of you to the second floor and take a left.\n" +
            "C1;Go to the third wing.\n" +
            "D1;Go to the forth wing at the end of the main corridor.\n" +
            "E1;Go into the corridor on the right.\n" +
            "F1;Go through both sets of doors to the end of the hallway.\n" +
            "A2;Take the elevator/staircase to the third floor and take a right.\n" +
            "B2;Take the elevator/staircase to the third floor and take a left.\n" +
            "C2;Go to the third wing.\n" +
            "D2;Go to the forth wing.\n" +
            "E2;Go into the corridor on the right.\n" +
            "A3;Take the elevator/staircase to the fourth floor and take a right.\n" +
            "B3;Take the elevator/staircase to the fourth floor and take a left.\n" +
            "C3;Go to the third wing.\n" +
            "D3;Go to the forth wing at the end of the main corridor."
}