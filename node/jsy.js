/*
 * This is a wrapper that executes a jsy program as a js program using
 * Node.js (Google's V8 JavaScript interpreter).
 * 
 * To use it, install Node.js for your platform and then type on the
 * command-line.
 *
 *   node jsy.js program.jsy
 *
 * where program.jsy is your jsy test program file.
 *
 * Version History:
 *   0.2 (09/23/2012): Changed printing of final value.
 *   0.1 (09/13/2012): Initial release.
 *
 */

const jsy = {
    print: console.log
};

const jsyprogram = (function () {
    const node = process.argv[0]
    const jsyjs = process.argv[1]
    const usage = function () {
	console.error("Usage: " + node + " " + jsyjs + " program.jsy");
	process.exit(1);
    };

    if (process.argv.length !== 3) {
	usage();
    }

    const fs = require('fs');
    const filename = process.argv[2];
    return fs.readFileSync(filename, 'utf8');
})();

const jsytojs = function (jsyprogram) {
    return jsyprogram;
};

const jsyvalue = eval(jsytojs(jsyprogram));
console.log(jsyvalue)
