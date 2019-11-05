# Арифметические выражения

Арифметические выражения с операциями сложения, вычитания,
умножения, скобками, унарным минусом. Приоритет операций стандартный.
В качестве операндов выступают целые числа. Используйте один терминал для всех чисел.

Пример: `(1+2)*(-3*(7-4)+2)`


## КС грамматика

> S -> \<Empty String\>  
> S -> E  
> E -> E + T  
> E -> E - T  
> E -> -(E)  
> E -> T  
> T -> T * F  
> T -> F  
> F -> n  
> F -> (E)  

Нетерминал | Описание
--- | ---
*S* | Стартовый нетерминал, может быть *E* или пустым
*E* | Арифметическое выражение, комбинирующееся из `+` и `-` или унарый минус над выражением
*T* | Арифметическое выражение, комбинирующееся из `*`
*F* | Новое *E* в скобках или терминал `n` (число)

## Устранение левой рекурсии и правого ветвления:

> S-> \<Empty String\>  
> S -> E  
> E -> b(E)E1  
> E -> TE1  
> E1 -> +TE1  
> E1 -> -TE1  
> E1 -> \<Empty String\>  
> T -> FT1  
> T1 -> *FT1  
> T1 -> \<Empty String\>  
> F -> n  
> F -> (E)  


Нетерминал | Описание
--- | ---
*S* | Стартовый нетерминал, может быть *E* или пустым
*E* | Арифметическое выражение, комбинирующееся из `+` и `-` или унарый минус над выражением
*E'* | Продолжение арифметического выражения *E*
*T* | Арифметическое выражение, комбинирующееся из `*`
*T'* | Продолжение арифметического выражения *T*
*F* | Новое *E* в скобках или терминал `n` (число)

## Лексер

Терминал | Лексема
--- | ---
*n* | *CONST*
*(* | *LPAREN*
*)* | *RPAREN*
*+* | *ADD*
*-* | *SUB*
*-()* | *NEGATIVE*
\* | *MUL*
*$* | *END*


## Синтаксический анализатор

Нетерминал | FIRST| FOLLOW
--- | --- | ---
*S* | (, n, - (унарный), *\<Empty String\>* | $
*E* | (, n, - (унарный)  | $, )
*E1'* | +, -, *\<Empty String\>* | $, )
*T* | (, n | +, -, $, )
*T1* | \*, *\<Empty String\>* | +, -, $, )
*F'* | (, n | *, +, -, $, )

## Тесты 

### Тесты без ошибок

Тест | Описание теста
--- | ---
*\<Empty String\>* | просто пустая строка
`1` | одиночное число 
`(1)` | одиночное число в скобках
`-1` | отрицательное одиночное число
`(-1)` | отрицательное одиночное число в скобках
`-(1)` | унарный минус перед числом в скобах
`3 + 3` | сложение 2 чисел
`3 - 3` | вычитание 2 чисел
`3 * 3` | умножение 2 чисел
`3 + 3 + 3` | сложение 3 чисел
`3 - 3 - 3` | вычитание 3 чисел
`3 * 3 * 3` | умножение 3 чисел
`3 * 3 - 3 + 3` | чередование знаков `*`, `+`, `-`
`3 - 3 * 3 + 3` | чередование знаков `*`, `+`, `-`
`3 - 3 + 3 * 3` | чередование знаков `*`, `+`, `-`
`3 - (3 + 3) * 3` | чередование знаков `*`, `+`, `-` + среднее число в (), тут не унарный минус
`3 - (-3 + 3) * 3` | чередование знаков `*`, `+`, `-` + среднее число в (), тут не унарный минус + число в сокбках отрицательное
`3 - (-3 * -3) * 3` | чередование знаков `*`, `+`, `-` + среднее число в (), тут не унарный минус + число в сокбках отрицательное
`15623 -793 * (27) * 11` | разные числа
`(((((((((((((13+13)))))))))))))` | много скобок
`(((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((7)))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))` | очень много скобок
`(-7)*(-7)` | умножение отрицательных чисел

### Тесты с ошибокой

Тест | Описание теста
--- | ---
`-` | голый `-` 
`+` | голый `+` 
`*` | голый `*` 
`*-` | голый `*-` 
`adsa ads` | непонятные символы 
`a123dsa` | число в буквах  
`+(3)` | плюс унарный  
`---3` | много минусов у этого примера (бадумс)  
`+++3` | много плюсов у этого примера (бадумс)  
`()` | пустые скобки  
`()()()()()` | много пустых скобок  
`(((((((((()` | не ПСП  