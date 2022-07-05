grammar fcp;

program : list*;

list : '(' (list | literal)* ')';

literal : INT
        | BOOLEAN
        | SYMBOL
        ;

INT : [0-9]+;
BOOLEAN : ('#t' | '#f');
SYMBOL : ~('#'|'"'|'\''|[()]|[ \t\r\n]) ~('"'|'\''|[()]|[ \t\r\n])* ;

WS : (' ' | '\r' | '\t' | '\n' | '\f')+ -> skip;