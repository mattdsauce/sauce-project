@echo off
echo starting prerun script
reg add "H K E Y _ C U R R E N T _ U S E R \ S o f t w a r e \ C l a s s e s \ L o c a l   S e t t i n g s \ S o f t w a r e \ M i c r o s o f t \ W i n d o w s \ C u r r e n t V e r s i o n \ A p p C o n t a i n e r \ S t o r a g e \ m i c r o s o f t . m i c r o s o f t e d g e _ 8 w e k y b 3 d 8 b b w e \ M i c r o s o f t E d g e \ M e d i a C a p t u r e\ A l l o w D o m a i n s " /v " h t t p s : / / w w w . o n l i n e m i c t e s t . c o m " /t REG_DWORD /D "0X 0 0 0 0 0 0 0 2 "
reg query "H K E Y _ C U R R E N T _ U S E R \ S o f t w a r e \ C l a s s e s \ L o c a l   S e t t i n g s \ S o f t w a r e \ M i c r o s o f t \ W i n d o w s \ C u r r e n t V e r s i o n \ A p p C o n t a i n e r \ S t o r a g e \ m i c r o s o f t . m i c r o s o f t e d g e _ 8 w e k y b 3 d 8 b b w e \ M i c r o s o f t E d g e \ M e d i a C a p t u r e\ " /s
echo finished prerun script
