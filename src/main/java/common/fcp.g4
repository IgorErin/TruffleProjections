grammar fcp;

program : list*;

list : '(' entry* ')';

entry : literal | list;

literal : INT
        | BOOLEAN
        | SYMBOL
        ;

INT : [0-9]+;
BOOLEAN : ('#t' | '#f');
SYMBOL : ~('#'|'"'|'\''|[()]|[ \t\r\n]) ~('"'|'\''|[()]|[ \t\r\n])* ;

WS : (' ' | '\r' | '\t' | '\n' | '\f')+ -> skip;