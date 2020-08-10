import svelte from 'rollup-plugin-svelte'
import scss from 'rollup-plugin-scss'
import autoPreprocess from 'svelte-preprocess';
import resolve from '@rollup/plugin-node-resolve'
import commonjs from '@rollup/plugin-commonjs'
import typescript from "@rollup/plugin-typescript"
// import ts from "@wessberg/rollup-plugin-ts";

let production = false

export default {
  input: 'src/main/resources/scripts/main.js',
  output: {
	sourcemap: !production,
    name: 'bundle',
    file: 'src/main/resources/static/bundle.js',
    format: 'iife',
    globals: ['types']
  },
  plugins: [
    typescript({}),
    svelte({
      // enable run-time checks when not in production
      dev: true,
      // we'll extract any component CSS out into
      // a separate file - better for performance
      css: css => {
        css.write('src/main/resources/static/bundle.css');
      },
      preprocess: autoPreprocess()
    }),
    scss({
      output: 'src/main/resources/static/global.css'
    }),
    resolve({
      browser: true,
      dedupe: ['svelte']
    }),
    commonjs({
    	include: /node_modules/,
    })
  ]
};